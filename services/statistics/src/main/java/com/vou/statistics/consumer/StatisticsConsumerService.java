package com.vou.statistics.consumer;

import lombok.AllArgsConstructor;

import com.vou.statistics.client.EventsServiceClient;
import com.vou.statistics.client.NotificationsServiceClient;
import com.vou.statistics.context.TransactionContext;
import com.vou.statistics.creator.TransactionFactoryCreator;
import com.vou.statistics.factory.TransactionFactory;
import com.vou.statistics.mapper.TransactionMapper;
import com.vou.statistics.dto.AddUsersRequestDto;
import com.vou.statistics.dto.EventDto;
import com.vou.statistics.dto.EventIdDto;
import com.vou.statistics.dto.ItemDto;
import com.vou.statistics.dto.TransactionDto;
import com.vou.statistics.dto.VoucherDto;
import com.vou.statistics.entity.ItemReceivedTransaction;
import com.vou.statistics.entity.ItemSharedTransaction;
import com.vou.statistics.model.Like;
import com.vou.statistics.model.NotificationData;
import com.vou.statistics.model.NotificationInfo;
import com.vou.statistics.model.Transaction;
import com.vou.statistics.repository.LikeRepository;
import com.vou.statistics.repository.TransactionRepository;
import com.vou.statistics.service.PlayerItemService;
import com.vou.statistics.service.PlayerVoucherService;
import com.vou.statistics.strategy.TransactionStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatisticsConsumerService {

    private PlayerVoucherService            playerVoucherService;
    private PlayerItemService               playerItemService;
    private NotificationsServiceClient      notificationsServiceClient;
    private EventsServiceClient             eventsServiceClient;
    private LikeRepository                  likeRepository;
    private TransactionRepository<ItemReceivedTransaction>      itemReceivedTransactionRepository;

    private static final Logger logger = Logger.getLogger(StatisticsConsumerService.class.getName());

    @Autowired
	private KafkaTemplate<String, NotificationData> kafkaTemplateNotificationInfo;

    @KafkaListener(topics = "session-transaction", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory")
    public void listenSessionTransaction(ConsumerRecord<String, TransactionDto> record, Acknowledgment acknowledgment) {
        try {
            // List<TransactionDto> transactionDtos = record.value();
            // List<Transaction> transactions = transactionDtos.stream()
            // .map(TransactionMapper::toEntity)
            // .collect(Collectors.toList());

            TransactionDto transactionDto = record.value();

            // convert to suitable TransactionDto

            List<CompletableFuture<Void>> futures = new ArrayList<>();

            // System.out.println("Transactions: " + transactions);

            // for (Transaction _transaction : transactions) {
            //     futures.add(processTransactionNotificationAsync(_transaction));
            // }

            futures.add(processTransactionNotificationAsync(TransactionMapper.toEntity(transactionDto)));
            
            // Wait for all CompletableFutures to complete
            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allOf.get(); // This will block until all async tasks are complete

            // Acknowledge after all processing is complete
            acknowledgment.acknowledge();
        }
        catch (Exception e) {
            e.getStackTrace();
        }
    }

    @KafkaListener(topics = "upcoming-event", groupId = "group_id", containerFactory = "eventKafkaListenerContainerFactory")
    public void listenUpcomingEvent(ConsumerRecord<String, EventIdDto> record, Acknowledgment acknowledgment) {
        try {
            EventIdDto eventIdDto = record.value();
            System.out.println("Event ID receive from Kafka: " + eventIdDto.getId());
            EventDto upcomingEvent = eventsServiceClient.getEventById(eventIdDto.getId());
            List<Like> likes = likeRepository.findByLikeableIdAndLikeableType(eventIdDto.getId(), "event");
            List<String> userIds = new ArrayList<>();
            for (Like like : likes) {
                userIds.add(like.getUserId());
            }

            NotificationInfo notificationInfo = new NotificationInfo("Upcoming event!", upcomingEvent.getName() + " event is coming.", "fa-check");
            NotificationData notificationData = new NotificationData(notificationInfo, userIds);

            System.out.println("Users: " + userIds);
            String notificationId = notificationsServiceClient.addUsersToNotification(new AddUsersRequestDto(notificationInfo, userIds));

            System.out.println("Notification IDDD: " + notificationId);
            kafkaTemplateNotificationInfo.send("event-notification", notificationData);

            acknowledgment.acknowledge();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public CompletableFuture<Void> processTransactionNotificationAsync(Transaction _transaction) {
        return CompletableFuture.runAsync(() -> {
            try {
                TransactionContext transactionContext = new TransactionContext();
                String artifactName = "";
                String artifactImage = "";

                TransactionFactory transactionFactory = TransactionFactoryCreator.getTransactionFactory(_transaction.getTransactionType());
                if (transactionFactory == null) {
                    throw new RuntimeException("Transaction factory not found for transaction type: " + _transaction.getTransactionType());
                }

                System.out.println("Transaction type: " + _transaction.getTransactionType());
                Transaction createdTransaction = transactionFactory.createTransaction(TransactionMapper.toDto(_transaction));  // Pass original transaction or additional data if needed
                System.out.println("Created transaction: " + createdTransaction);
                // Create the transaction based on the factory
                TransactionStrategy transactionStrategy = TransactionFactoryCreator.getTransactionStrategy(_transaction.getTransactionType());
                transactionContext.setTransactionStrategy(transactionStrategy);

                // find in the item
                List<ItemDto> items = eventsServiceClient.getItemsByIds(Collections.singletonList(createdTransaction.getArtifactId()));
                if (items.isEmpty()) {
                    // find in the voucher
                    List<VoucherDto> vouchers = eventsServiceClient.getVouchersByIds(Collections.singletonList(createdTransaction.getArtifactId()));
                    if (vouchers.isEmpty()) {
                        throw new RuntimeException("Item or voucher not found for transaction: " + _transaction);
                    } else {
                        artifactName = vouchers.get(0).getVoucherCode();
                        artifactImage = vouchers.get(0).getImage();
                    }
                } else {
                    artifactName = items.get(0).getName();
                    artifactImage = items.get(0).getIcon();
                }

                if (transactionContext.executeStrategy(_transaction, playerVoucherService, playerItemService, eventsServiceClient, null)) {
                    System.out.println("Transaction processed successfully");

                    NotificationInfo notificationInfo = new NotificationInfo("You've received item " + artifactName, "Check your inventory for updates", artifactImage);
                    NotificationData notificationData = new NotificationData(notificationInfo, Collections.singletonList(_transaction.getRecipientId()));
                    String notificationId = notificationsServiceClient.addUsersToNotification(new AddUsersRequestDto(notificationInfo, Collections.singletonList(_transaction.getRecipientId())));
                    
                    System.out.println("Notification IDDD: " + notificationId);

                    // notificationsServiceClient.sendNotification(notificationData);
                    kafkaTemplateNotificationInfo.send("event-notification", notificationData);
                } else {
                    System.out.println("Transaction processing failed");
                }

                itemReceivedTransactionRepository.save((ItemReceivedTransaction) createdTransaction);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}