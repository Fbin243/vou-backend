package com.vou.statistics.mapper;

import org.springframework.stereotype.Service;

import com.vou.statistics.dto.PlayerItemDto;
import com.vou.statistics.entity.PlayerItem;

@Service
public class PlayerItemMapper {
        
    // Convert PlayerItem to PlayerItemDto
    public static PlayerItemDto toDto(PlayerItem playerItem) {
        if (playerItem == null) {
            return null;
        }

        PlayerItemDto playerItemDto = new PlayerItemDto();
        playerItemDto.setPlayerId(playerItem.getPlayerId());
        playerItemDto.setItemId(playerItem.getItemId());
        playerItemDto.setBrandId(playerItem.getBrandId());
        playerItemDto.setItemName(playerItem.getItemName());
        playerItemDto.setGameId(playerItem.getGameId());
        playerItemDto.setQuantity(playerItem.getQuantity());

        return playerItemDto;
    }

    // Convert PlayerItemDto to PlayerItem
    public static PlayerItem toEntity(PlayerItemDto dto) {
        if (dto == null) {
            return null;
        }

        PlayerItem playerItem = new PlayerItem();
        playerItem.setPlayerId(dto.getPlayerId());
        playerItem.setItemId(dto.getItemId());
        playerItem.setBrandId(dto.getBrandId());
        playerItem.setItemName(dto.getItemName());
        playerItem.setGameId(dto.getGameId());
        playerItem.setQuantity(dto.getQuantity());

        return playerItem;
    }
}
