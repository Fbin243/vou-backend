package com.vou.statistics.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.vou.statistics.model.NotificationData;
import com.vou.statistics.model.NotificationInfo;
import com.vou.statistics.model.Transaction;
import com.vou.statistics.repository.TransactionRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vou.statistics.client.EventsServiceClient;
import com.vou.statistics.client.NotificationsServiceClient;
import com.vou.statistics.context.TransactionContext;
import com.vou.statistics.creator.TransactionFactoryCreator;
import com.vou.statistics.dto.AddUsersRequestDto;
import com.vou.statistics.dto.ItemDto;
import com.vou.statistics.dto.TransactionDto;
import com.vou.statistics.dto.VoucherDto;
import com.vou.statistics.entity.ItemSharedTransaction;
import com.vou.statistics.entity.ItemReceivedTransaction;
import com.vou.statistics.entity.VoucherConversionTransaction;
import com.vou.statistics.entity.VoucherUsedTransaction;
import com.vou.statistics.factory.TransactionFactory;
import com.vou.statistics.mapper.TransactionMapper;
import com.vou.statistics.service.PlayerItemService;
import com.vou.statistics.service.PlayerVoucherService;
// import com.vou.statistics.mapper.TransactionMapper;
import com.vou.statistics.service.TransactionService;
import com.vou.statistics.strategy.TransactionStrategy;

import lombok.AllArgsConstructor;

import java.util.Collections;

@AllArgsConstructor
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    private TransactionService<ItemSharedTransaction>           itemSharedTransactionService;
    private TransactionService<ItemReceivedTransaction>         itemReceivedTransactionService;
    private TransactionService<VoucherUsedTransaction>          voucherUsedTransactionService;
    private TransactionService<VoucherConversionTransaction>    voucherConversionTransactionService;
    private PlayerVoucherService                                playerVoucherService;
    private PlayerItemService                                   playerItemService;
    private EventsServiceClient                                 eventsServiceClient;
    private NotificationsServiceClient                          notificationsServiceClient;
    private TransactionRepository<ItemSharedTransaction>        itemSharedTransactionRepository;
    private TransactionRepository<ItemReceivedTransaction>      itemReceivedTransactionRepository;
    private TransactionRepository<VoucherUsedTransaction>       voucherUsedTransactionRepository;
    private TransactionRepository<VoucherConversionTransaction> voucherConversionTransactionRepository;

    @Autowired
	private KafkaTemplate<String, NotificationData> kafkaTemplateNotificationInfo;

    // @Autowired
    // public TransactionController(TransactionService<ItemSharedTransaction> itemSharedTransactionService, TransactionService<VoucherUsedTransaction> voucherUsedTransactionService, TransactionService<VoucherConversionTransaction> voucherConversionTransactionService, PlayerVoucherService playerVoucherService, PlayerItemService playerItemService, EventsServiceClient eventsServiceClient) {
    //     this.itemSharedTransactionService = itemSharedTransactionService;
    //     this.voucherUsedTransactionService = voucherUsedTransactionService;
    //     this.voucherConversionTransactionService = voucherConversionTransactionService;
    //     this.playerVoucherService = playerVoucherService;
    //     this.playerItemService = playerItemService;
    //     this.eventsServiceClient = eventsServiceClient;
    // }

    @GetMapping("/item_shared")
    public ResponseEntity<List<ItemSharedTransaction>> getAllItemSharedTransactions() {
        return ResponseEntity.ok(itemSharedTransactionService.getAllTransactions());
    }

    @GetMapping("/item_received")
    public ResponseEntity<List<ItemReceivedTransaction>> getAllItemReceivedTransactions() {
        return ResponseEntity.ok(itemReceivedTransactionService.getAllTransactions());
    }

    @GetMapping("/voucher_used")
    public ResponseEntity<List<VoucherUsedTransaction>> getAllVoucherUsedTransactions() {
        return ResponseEntity.ok(voucherUsedTransactionService.getAllTransactions());
    }

    @GetMapping("/voucher_conversion")
    public ResponseEntity<List<VoucherConversionTransaction>> getAllVoucherConversionTransactions() {
        return ResponseEntity.ok(voucherConversionTransactionService.getAllTransactions());
    }

    @GetMapping("/item_shared/{id}")
    public ResponseEntity<ItemSharedTransaction> getTransactionById(@PathVariable String id) {
        if (id.length() != 24) {
            return ResponseEntity.badRequest().body(null); // Or return a custom error message
        }

        return ResponseEntity.ok(itemSharedTransactionService.getTransactionById(new ObjectId(id)));
    }

    @GetMapping("/item_received/{id}")
    public ResponseEntity<ItemReceivedTransaction> getItemReceivedTransactionById(@PathVariable String id) {
        if (id.length() != 24) {
            return ResponseEntity.badRequest().body(null); // Or return a custom error message
        }

        return ResponseEntity.ok(itemReceivedTransactionService.getTransactionById(new ObjectId(id)));
    }

    @GetMapping("/voucher_used/{id}")
    public ResponseEntity<VoucherUsedTransaction> getVoucherUsedTransactionById(@PathVariable String id) {
        if (id.length() != 24) {
            return ResponseEntity.badRequest().body(null); // Or return a custom error message
        }

        return ResponseEntity.ok(voucherUsedTransactionService.getTransactionById(new ObjectId(id)));
    }

    @GetMapping("/voucher_conversion/{id}")
    public ResponseEntity<VoucherConversionTransaction> getVoucherConversionTransactionById(@PathVariable String id) {
        if (id.length() != 24) {
            return ResponseEntity.badRequest().body(null); // Or return a custom error message
        }

        return ResponseEntity.ok(voucherConversionTransactionService.getTransactionById(new ObjectId(id)));
    }

    @GetMapping("/item_shared/sender/{senderId}")
    public ResponseEntity<List<ItemSharedTransaction>> getTransactionsBySender(@PathVariable String senderId) {
        return ResponseEntity.ok(itemSharedTransactionService.getTransactionsByPlayer(senderId));
    }

    @GetMapping("/voucher_used/sender/{senderId}")
    public ResponseEntity<List<VoucherUsedTransaction>> getVoucherUsedTransactionsBySender(@PathVariable String senderId) {
        return ResponseEntity.ok(voucherUsedTransactionService.getTransactionsByPlayer(senderId));
    }

    @GetMapping("/voucher_conversion/sender/{senderId}")
    public ResponseEntity<List<VoucherConversionTransaction>> getVoucherConversionTransactionsBySender(@PathVariable String senderId) {
        return ResponseEntity.ok(voucherConversionTransactionService.getTransactionsByPlayer(senderId));
    }

    @GetMapping("/item_shared/recipient/{recipientId}")
    public ResponseEntity<List<ItemSharedTransaction>> getTransactionsByRecipient(@PathVariable String recipientId) {
        return ResponseEntity.ok(itemSharedTransactionService.getTransactionsByRecipient(recipientId));
    }

    @GetMapping("/item_received/recipient/{recipientId}")
    public ResponseEntity<List<ItemReceivedTransaction>> getItemReceivedTransactionsByRecipient(@PathVariable String recipientId) {
        return ResponseEntity.ok(itemReceivedTransactionService.getTransactionsByRecipient(recipientId));
    }

    @GetMapping("/voucher_used/recipient/{recipientId}")
    public ResponseEntity<List<VoucherUsedTransaction>> getVoucherUsedTransactionsByRecipient(@PathVariable String recipientId) {
        return ResponseEntity.ok(voucherUsedTransactionService.getTransactionsByRecipient(recipientId));
    }

    @GetMapping("/voucher_conversion/recipient/{recipientId}")
    public ResponseEntity<List<VoucherConversionTransaction>> getVoucherConversionTransactionsByRecipient(@PathVariable String recipientId) {
        return ResponseEntity.ok(voucherConversionTransactionService.getTransactionsByRecipient(recipientId));
    }

    @GetMapping("/item_shared/sender/{senderId}/recipient/{recipientId}")
    public ResponseEntity<List<ItemSharedTransaction>> getTransactionsBySenderAndRecipient(@PathVariable String senderId, @PathVariable String recipientId) {
        return ResponseEntity.ok(itemSharedTransactionService.getTransactionsBySenderAndRecipient(senderId, recipientId));
    }

    @GetMapping("/voucher_used/sender/{senderId}/recipient/{recipientId}")
    public ResponseEntity<List<VoucherUsedTransaction>> getVoucherUsedTransactionsBySenderAndRecipient(@PathVariable String senderId, @PathVariable String recipientId) {
        return ResponseEntity.ok(voucherUsedTransactionService.getTransactionsBySenderAndRecipient(senderId, recipientId));
    }

    @GetMapping("/voucher_conversion/sender/{senderId}/recipient/{recipientId}")
    public ResponseEntity<List<VoucherConversionTransaction>> getVoucherConversionTransactionsBySenderAndRecipient(@PathVariable String senderId, @PathVariable String recipientId) {
        return ResponseEntity.ok(voucherConversionTransactionService.getTransactionsBySenderAndRecipient(senderId, recipientId));
    }

    @GetMapping("/item_shared/artifact/{artifactId}")
    public ResponseEntity<List<ItemSharedTransaction>> getTransactionsByArtifact(@PathVariable String artifactId) {
        return ResponseEntity.ok(itemSharedTransactionService.getTransactionsByArtifact(artifactId));
    }

    @GetMapping("/item_received/artifact/{artifactId}")
    public ResponseEntity<List<ItemReceivedTransaction>> getItemReceivedTransactionsByArtifact(@PathVariable String artifactId) {
        return ResponseEntity.ok(itemReceivedTransactionService.getTransactionsByArtifact(artifactId));
    }

    @GetMapping("/voucher_used/artifact/{artifactId}")
    public ResponseEntity<List<VoucherUsedTransaction>> getVoucherUsedTransactionsByArtifact(@PathVariable String artifactId) {
        return ResponseEntity.ok(voucherUsedTransactionService.getTransactionsByArtifact(artifactId));
    }

    @GetMapping("/voucher_conversion/artifact/{artifactId}")
    public ResponseEntity<List<VoucherConversionTransaction>> getVoucherConversionTransactionsByArtifact(@PathVariable String artifactId) {
        return ResponseEntity.ok(voucherConversionTransactionService.getTransactionsByArtifact(artifactId));
    }
    
    // usually one happens at a time
    @PostMapping("/process")
    public ResponseEntity<Boolean> processTransactions(@RequestBody List<TransactionDto> transactionDtos) {
        System.out.println("Processing transactions...");
        List<Transaction> transactions = transactionDtos.stream()
            .map(TransactionMapper::toEntity)
            .collect(Collectors.toList());

        System.out.println("Transactions: " + transactions);

        for (Transaction _transaction : transactions) {
            TransactionContext transactionContext = new TransactionContext();

            TransactionFactory transactionFactory = TransactionFactoryCreator.getTransactionFactory(_transaction.getTransactionType());
            if (transactionFactory == null) {
                // Handle the case where no factory is found
                return ResponseEntity.ok(false);
            }

            // _transaction = transactionFactory.createTransaction(); // not sure
            // transactionContext.setTransactionStrategy(TransactionFactoryCreator.getTransactionStrategy(_transaction.getTransactionType()));
            // if (transactionContext.executeStrategy(_transaction) == false) {
            //     return ResponseEntity.ok(false);
            // }
            System.out.println("Transaction type: " + _transaction.getTransactionType());
            // // Create the transaction based on the factory
            Transaction createdTransaction = transactionFactory.createTransaction(TransactionMapper.toDto(_transaction));  // Pass original transaction or additional data if needed
            System.out.println("Created transaction: " + createdTransaction);
            // // Create the transaction based on the factory
            TransactionStrategy transactionStrategy = TransactionFactoryCreator.getTransactionStrategy(_transaction.getTransactionType());
            transactionContext.setTransactionStrategy(transactionStrategy);

            /////
            // find in the item
            String artifactName = "";
            String artifactImage = "";

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


            if (createdTransaction.getTransactionType().equalsIgnoreCase("voucher_conversion")) {
                if (transactionContext.executeStrategy(createdTransaction, playerVoucherService, playerItemService, eventsServiceClient, null) == false) {
                    System.out.println("Transaction processed successfully");

                    NotificationInfo notificationInfo = new NotificationInfo("You have new voucher " + artifactName + " successfully!", "Check your inventory for updates", artifactImage);
                    NotificationData notificationData = new NotificationData(notificationInfo, Collections.singletonList(_transaction.getRecipientId()));
                    String notificationId = notificationsServiceClient.addUsersToNotification(new AddUsersRequestDto(notificationInfo, Collections.singletonList(_transaction.getRecipientId())));
                    
                    System.out.println("Notification IDDD: " + notificationId);
    
                    // notificationsServiceClient.sendNotification(notificationData);
                    kafkaTemplateNotificationInfo.send("event-notification", notificationData);

                    return ResponseEntity.ok(false);
                }
            } else if (createdTransaction.getTransactionType().equalsIgnoreCase("voucher_used")) {
                if (transactionContext.executeStrategy(createdTransaction, playerVoucherService, playerItemService, eventsServiceClient, null) == false) {
                    return ResponseEntity.ok(false);
                }
                System.out.println("Transaction processed successfully");

                NotificationInfo notificationInfo = new NotificationInfo("You've used voucher " + artifactName + " successfully!", "Check your inventory for updates", artifactImage);
                NotificationData notificationData = new NotificationData(notificationInfo, Collections.singletonList(_transaction.getRecipientId()));
                String notificationId = notificationsServiceClient.addUsersToNotification(new AddUsersRequestDto(notificationInfo, Collections.singletonList(_transaction.getRecipientId())));
                
                System.out.println("Notification IDDD: " + notificationId);

                // notificationsServiceClient.sendNotification(notificationData);
                kafkaTemplateNotificationInfo.send("event-notification", notificationData);
            } else {
                if (transactionContext.executeStrategy(createdTransaction, playerVoucherService, playerItemService, eventsServiceClient, null) == false) {
                    return ResponseEntity.ok(false);
                }
            }

            // save transactions
            if (createdTransaction.getTransactionType().equalsIgnoreCase("item_shared")) {
                itemSharedTransactionRepository.save((ItemSharedTransaction) createdTransaction);
            } else if (createdTransaction.getTransactionType().equalsIgnoreCase("item_received")) {
                itemReceivedTransactionRepository.save((ItemReceivedTransaction) createdTransaction);
            } else if (createdTransaction.getTransactionType().equalsIgnoreCase("voucher_used")) {
                voucherUsedTransactionRepository.save((VoucherUsedTransaction) createdTransaction);
            } else if (createdTransaction.getTransactionType().equalsIgnoreCase("voucher_conversion")) {
                voucherConversionTransactionRepository.save((VoucherConversionTransaction) createdTransaction);
            }
        }

        return ResponseEntity.ok(true);
    }

    // @PostMapping("/vouchers/process")
    // public ResponseEntity<Boolean> processVoucherTransaction(@RequestBody VoucherItemsConversionInfo voucherItemsConversionInfo) {
    //     return ResponseEntity.ok(transactionService.processVoucherTransaction(voucherItemsConversionInfo));
    // }
}
