package com.vou.statistics.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vou.statistics.dto.ItemDto;
import com.vou.statistics.dto.PlayerDto;
import com.vou.statistics.dto.PlayerItemsDto;
import com.vou.statistics.dto.PlayerVouchersDto;
import com.vou.statistics.dto.Player_ItemQuantitiesDto;
import com.vou.statistics.dto.Player_VoucherQuantitiesDto;
import com.vou.statistics.dto.VoucherDto;
import com.vou.statistics.entity.PlayerItem;
import com.vou.statistics.entity.PlayerVoucher;
import com.vou.statistics.service.PlayerItemService;
import com.vou.statistics.service.PlayerVoucherService;

@RestController
@RequestMapping(path = "/api/statistics", produces = {MediaType.APPLICATION_JSON_VALUE})
public class StatisticsController {
    
    private PlayerVoucherService    playerVoucherService;
    private PlayerItemService       playerItemService;

    public StatisticsController(PlayerVoucherService playerVoucherService, PlayerItemService playerItemService) {
        this.playerVoucherService = playerVoucherService;
        this.playerItemService = playerItemService;
    }

    @GetMapping("/player_voucher/player/{playerId}")
    public ResponseEntity<List<VoucherDto>> getVouchersByPlayer(@PathVariable String playerId) {
        return ResponseEntity.ok(playerVoucherService.getVouchersByPlayer(playerId));
    }

    @GetMapping("/player_voucher/voucher/{voucherId}")
    public ResponseEntity<List<PlayerDto>> getPlayersByVoucher(@PathVariable String voucherId) {
        return ResponseEntity.ok(playerVoucherService.getPlayersByVoucher(voucherId));
    }

    @PostMapping("/player_voucher")
    public ResponseEntity<List<PlayerVoucher>> savePlayerVouchers(@RequestBody Player_VoucherQuantitiesDto dto) {
        List<PlayerVoucher> playerVouchers = playerVoucherService.addPlayerVouchers(dto);
        return ResponseEntity.ok(playerVouchers);
    }

    @DeleteMapping("/player_voucher")
    public ResponseEntity<Boolean> deletePlayerVouchers(@RequestBody PlayerVouchersDto dto) {
        return ResponseEntity.ok(playerVoucherService.deletePlayerVouchers(dto));
    }
    
    @GetMapping("/player_item/player/{playerId}")
    public ResponseEntity<List<ItemDto>> getItemsByPlayer(@PathVariable String playerId) {
        return ResponseEntity.ok(playerItemService.getItemsByPlayer(playerId));
    }

    @GetMapping("/player_item/item/{itemId}")
    public ResponseEntity<List<PlayerDto>> getPlayersByItem(@PathVariable String itemId) {
        return ResponseEntity.ok(playerItemService.getPlayersByItem(itemId));
    }

    @PostMapping("/player_item")
    public ResponseEntity<List<PlayerItem>> savePlayerItems(@RequestBody Player_ItemQuantitiesDto dto) {
        List<PlayerItem> playerItemsTmp = playerItemService.addPlayerItems(dto);
        System.out.println("playerItemsTmp: " + playerItemsTmp);
        List<PlayerItem> playerItems = new ArrayList<>();
        for (PlayerItem playerItem : playerItemsTmp) {
            if (playerItem.getQuantity() > 0) {
                playerItems.add(playerItem);
            }
        }
        return ResponseEntity.ok(playerItems);
    }

    @DeleteMapping("/player_item")
    public ResponseEntity<Boolean> deletePlayerItems(@RequestBody PlayerItemsDto dto) {
        return ResponseEntity.ok(playerItemService.deletePlayerItems(dto));
    }
}
