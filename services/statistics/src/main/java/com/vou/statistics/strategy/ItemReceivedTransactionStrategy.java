package com.vou.statistics.strategy;

import com.vou.statistics.client.EventsServiceClient;
import com.vou.statistics.dto.ItemDto;
import com.vou.statistics.dto.PlayerItemDto;
import com.vou.statistics.entity.ItemReceivedTransaction;
import com.vou.statistics.model.Transaction;
import com.vou.statistics.repository.ItemReceivedTransactionRepository;
import com.vou.statistics.repository.TransactionRepository;
import com.vou.statistics.service.PlayerItemService;
import com.vou.statistics.service.PlayerVoucherService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.vou.statistics.common.Constants.TRANSACTION_TYPE_ITEM_RECEIVED;

import java.util.Collections;

@Service
@NoArgsConstructor
// @AllArgsConstructor
public class ItemReceivedTransactionStrategy implements TransactionStrategy {
	
	private PlayerItemService playerItemService;
	
	@Autowired
	private ItemReceivedTransactionRepository itemReceivedTransactionRepository;
	
	@Autowired
	public ItemReceivedTransactionStrategy(PlayerItemService playerItemService,
	                                       ItemReceivedTransactionRepository itemReceivedTransactionRepository) {
		this.playerItemService = playerItemService;
		this.itemReceivedTransactionRepository = itemReceivedTransactionRepository;
	}
	
	@Override
	public boolean processTransaction(Transaction transaction, PlayerVoucherService playerVoucherService,
	                                  PlayerItemService _playerItemService, EventsServiceClient eventsServiceClient, TransactionRepository<Transaction> transactionRepository) {
		if (!transaction.getTransactionType().equalsIgnoreCase(TRANSACTION_TYPE_ITEM_RECEIVED)) {
			throw new IllegalArgumentException("Invalid transaction type for ItemReceivedTransactionStrategy");
		}
		
		try {
			ItemReceivedTransaction itemReceivedTransaction = (ItemReceivedTransaction) transaction;
			// get item by id
            ItemDto currentItem = eventsServiceClient.getItemsByIds(Collections.singletonList(transaction.getArtifactId())).get(0);

            if (currentItem == null) {
                System.out.println("Item not found in database!");
                return false;
            }

			System.out.println("ItemReceivedTransactionStrategy.processTransaction " + _playerItemService);
			_playerItemService.addPlayerItem(new PlayerItemDto(transaction.getRecipientId(), transaction.getArtifactId(), currentItem.getBrand_id(), currentItem.getName(), itemReceivedTransaction.getGameId(), transaction.getQuantity()));
			// playerItemService.deletePlayerItem(new PlayerItemDto(transaction.getPlayerId(), transaction.getArtifactId(), transaction.getQuantity()));
			System.out.println("Passed addPlayerItem");
			saveTransaction(transaction, transactionRepository);
			System.out.println("ItemReceivedTransactionStrategy.processTransaction " + transaction);
		} catch (Exception e) {
			e.getStackTrace();
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean saveTransaction(Transaction transaction, TransactionRepository<Transaction> transactionRepository) {
		if (!transaction.getTransactionType().equalsIgnoreCase(TRANSACTION_TYPE_ITEM_RECEIVED)) {
			throw new IllegalArgumentException("Invalid transaction type for ItemReceivedTransactionStrategy");
		}
		
		try {
			ItemReceivedTransaction itemReceivedTransaction = (ItemReceivedTransaction) transaction;
			itemReceivedTransactionRepository.save(itemReceivedTransaction);
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		}
		
		return true;
	}
}
