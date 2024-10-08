package com.vou.statistics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.NoArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vou.statistics.client.EventsServiceClient;
import com.vou.statistics.client.UsersServiceClient;
import com.vou.statistics.dto.ItemDto;
import com.vou.statistics.dto.PlayerDto;
import com.vou.statistics.dto.PlayerItemDto;
import com.vou.statistics.dto.Player_ItemQuantitiesDto;
import com.vou.statistics.dto.ReturnItemDto;
import com.vou.statistics.entity.PlayerItem;
import com.vou.statistics.repository.PlayerItemRepository;

@Service
@NoArgsConstructor
//@AllArgsConstructor
public class PlayerItemService implements IPlayerItemService {
    
    @Autowired
    private PlayerItemRepository        playerItemRepository;

    @Autowired
    private UsersServiceClient          usersServiceClient;

    @Autowired
    private EventsServiceClient         eventsServiceClient;

    @Autowired
    public PlayerItemService(PlayerItemRepository playerItemRepository) {
        this.playerItemRepository = playerItemRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(PlayerItemService.class);

    // @Autowired
    public PlayerItemService(PlayerItemRepository playerItemRepository, UsersServiceClient usersServiceClient, EventsServiceClient eventsServiceClient) {
        this.playerItemRepository = playerItemRepository;
        this.usersServiceClient = usersServiceClient;
        this.eventsServiceClient = eventsServiceClient;
    }

    @Override
    public List<PlayerItem> getByPlayerId(String playerId) {
        return playerItemRepository.findByPlayerId(playerId);
    }

    @Override
    public List<PlayerItem> getByItemId(String itemId) {
        return playerItemRepository.findByItemId(itemId);
    }

    @Override
    // public List<ReturnItemDto> getItemsByPlayer(String playerId) {
    //     List<PlayerItem> playerItems = playerItemRepository.findByPlayerId(playerId);
    //     List<ItemDto> items = eventsServiceClient.getItemsByIds(playerItems.stream().map(PlayerItem::getItemId).collect(Collectors.toList()));
        
    //     List<ReturnItemDto> returnItemDtos = new ArrayList<>();

    //     for (int i = 0; i < items.size(); ++i) {
    //         returnItemDtos.add(new ReturnItemDto(items.get(i), playerItems.get(i).getQuantity()));
    //     }

    //     return returnItemDtos;
    // }
    public List<ReturnItemDto> getItemsByPlayer(String playerId) {
        List<PlayerItem> playerItems = playerItemRepository.findByPlayerId(playerId);
    
        // Create a list of item IDs from playerItems
        List<String> itemIds = playerItems.stream()
                                          .map(PlayerItem::getItemId)
                                          .collect(Collectors.toList());
    
        // Fetch the items based on itemIds
        List<ItemDto> items = eventsServiceClient.getItemsByIds(itemIds);
    
        // Map items by their id for easy lookup
        Map<String, ItemDto> itemMap = items.stream()
                                            .collect(Collectors.toMap(ItemDto::getId, item -> item));
    
        List<ReturnItemDto> returnItemDtos = new ArrayList<>();
    
        // Match playerItems with their corresponding items using itemMap
        for (PlayerItem playerItem : playerItems) {
            ItemDto correspondingItem = itemMap.get(playerItem.getItemId());
            if (correspondingItem != null) {
                returnItemDtos.add(new ReturnItemDto(correspondingItem, playerItem.getQuantity()));
            }
        }
    
        return returnItemDtos;
    }
    

    @Override
    public List<PlayerDto> getPlayersByItem(String itemId) {
        List<PlayerItem> playerItems = playerItemRepository.findByItemId(itemId);
        List<PlayerDto> players = usersServiceClient.getManyPlayersByManyIds(playerItems.stream().map(PlayerItem::getPlayerId).collect(Collectors.toList()));
        return players;
    }

    @Override
    public PlayerItem addPlayerItem(PlayerItemDto dto) {
        // playerItemRepository = new PlayerItemRepository();
        Optional<PlayerItem> existingPlayerItem = playerItemRepository.findByPlayerIdAndItemIdAndGameId(dto.getPlayerId(), dto.getItemId(), dto.getGameId());
        if (existingPlayerItem.isPresent()) {
            // If the record exists, update the quantity
            PlayerItem playerItem = existingPlayerItem.get();
            playerItem.setQuantity(Math.max(0, playerItem.getQuantity() + dto.getQuantity()));
            return playerItemRepository.save(playerItem);
        } else {
            // If the record does not exist, create a new one
            PlayerItem newPlayerItem = new PlayerItem();
            newPlayerItem.setPlayerId(dto.getPlayerId());
            newPlayerItem.setItemId(dto.getItemId());
            newPlayerItem.setQuantity(dto.getQuantity());
            newPlayerItem.setBrandId(dto.getBrandId());
            newPlayerItem.setItemName(dto.getItemName());
            newPlayerItem.setGameId(dto.getGameId());
            return playerItemRepository.save(newPlayerItem);
        }
    }

    @Override
    public List<PlayerItem> addPlayerItems(Player_ItemQuantitiesDto dto) {
        return dto.getPlayerItems().stream().map(this::addPlayerItem).collect(Collectors.toList());
    }

    @Override
    public Boolean deletePlayerItem(PlayerItemDto dto) {
        try {
            // set quantity to 0
            Optional<PlayerItem> existingPlayerItem = playerItemRepository.findByPlayerIdAndItemIdAndGameId(dto.getPlayerId(), dto.getItemId(), dto.getGameId());
            if (existingPlayerItem.isPresent()) {
                PlayerItem playerItem = existingPlayerItem.get();
                playerItem.setQuantity(0);
                playerItemRepository.save(playerItem);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public Integer getQuantityByPlayerIdAndItemIdAndGameId(String playerId, String itemId, Long gameId) {
        Optional<PlayerItem> playerItem = playerItemRepository.findByPlayerIdAndItemIdAndGameId(playerId, itemId, gameId);
        return playerItem.map(PlayerItem::getQuantity).orElse(0);
    }

    // check later
    // @Override
    // public Boolean deletePlayerItems(PlayerItemsDto dto) {
    //     try {
    //         // set quantity to 0
    //         List<PlayerItem> playerItems = playerItemRepository.findByPlayerIdAndItemIds(dto.getPlayerId(), dto.getItemIds());
    //         playerItems.forEach(playerItem -> playerItem.setQuantity(0));
    //         playerItemRepository.saveAll(playerItems);
    //     }
    //     catch (Exception e) {
    //         e.printStackTrace();
    //         return false;
    //     }

    //     return true;
    // }
}
