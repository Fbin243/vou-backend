package com.vou.statistics.strategy;

import com.vou.statistics.client.EventsServiceClient;
import com.vou.statistics.dto.PlayerItemDto;
import com.vou.statistics.entity.ItemReceivedTransaction;
import com.vou.statistics.model.Transaction;
import com.vou.statistics.repository.ItemReceivedTransactionRepository;
import com.vou.statistics.service.PlayerItemService;
import com.vou.statistics.service.PlayerVoucherService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import static com.vou.statistics.common.Constants.TRANSACTION_TYPE_ITEM_RECEIVED;

@NoArgsConstructor
// @AllArgsConstructor
public class ItemReceivedTransactionStrategy implements TransactionStrategy {
	
	private PlayerItemService playerItemService;
	private ItemReceivedTransactionRepository itemReceivedTransactionRepository;
	
	
	@Autowired
	public ItemReceivedTransactionStrategy(PlayerItemService playerItemService,
	                                       ItemReceivedTransactionRepository itemReceivedTransactionRepository) {
		this.playerItemService = playerItemService;
		this.itemReceivedTransactionRepository = itemReceivedTransactionRepository;
	}
	
	@Override
	public boolean processTransaction(Transaction transaction, PlayerVoucherService playerVoucherService,
	                                  PlayerItemService _playerItemService, EventsServiceClient eventsServiceClient) {
		if (!transaction.getTransactionType().equalsIgnoreCase(TRANSACTION_TYPE_ITEM_RECEIVED)) {
			throw new IllegalArgumentException("Invalid transaction type for ItemReceivedTransactionStrategy");
		}
		
		try {
			// System.out.println("ItemReceivedTransactionStrategy.processTransaction " + _playerItemService);
			_playerItemService.addPlayerItem(new PlayerItemDto(transaction.getRecipientId(), transaction.getArtifactId(), transaction.getQuantity()));
			// playerItemService.deletePlayerItem(new PlayerItemDto(transaction.getPlayerId(), transaction.getArtifactId(), transaction.getQuantity()));
			// System.out.println("Passed addPlayerItem");
			saveTransaction(transaction);
			// System.out.println("ItemReceivedTransactionStrategy.processTransaction " + transaction);
		} catch (Exception e) {
			e.getStackTrace();
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean saveTransaction(Transaction transaction) {
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
