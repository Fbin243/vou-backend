package com.vou.statistics.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vou.statistics.dto.PlayerDto;
import com.vou.statistics.dto.PlayerGameEventDto;
import com.vou.statistics.dto.PlayerItemsDto;
import com.vou.statistics.dto.PlayerVouchersDto;
import com.vou.statistics.dto.Player_ItemQuantitiesDto;
import com.vou.statistics.dto.Player_VoucherQuantitiesDto;
import com.vou.statistics.dto.ReturnItemDto;
import com.vou.statistics.dto.VoucherDto;
import com.vou.statistics.entity.PlayerGameEvent;
import com.vou.statistics.entity.PlayerItem;
import com.vou.statistics.entity.PlayerVoucher;
import com.vou.statistics.model.Like;
import com.vou.statistics.service.LikeService;
import com.vou.statistics.service.PlayerGameEventService;
import com.vou.statistics.service.PlayerItemService;
import com.vou.statistics.service.PlayerVoucherService;

@RestController
@RequestMapping(path = "/api/statistics", produces = {MediaType.APPLICATION_JSON_VALUE})
public class StatisticsController {
    
    @Autowired
    private LikeService             likeService;
    private PlayerVoucherService    playerVoucherService;
    private PlayerItemService       playerItemService;
    private PlayerGameEventService  playerGameEventService;

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

    @GetMapping("/likeable")
    public ResponseEntity<List<Like>> getLikes(@RequestParam String likeableId,
                                               @RequestParam String likeableType) {
        List<Like> likes = likeService.getLikesForLikeable(likeableId, likeableType);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/likeable/user")
    public ResponseEntity<List<Like>> getLikesForUser(@RequestParam String userId,
                                                        @RequestParam String likeableType) {
        List<Like> likes = likeService.getLikesForUser(userId, likeableType);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/already_liked")
    public ResponseEntity<Boolean> alreadyLiked(@RequestParam String userId,
                                                @RequestParam String likeableId,
                                                @RequestParam String likeableType) {
        return ResponseEntity.ok(likeService.isLiked(userId, likeableType, likeableId));
    }

    @GetMapping("/player_game_event/player/event/{eventId}")
    public ResponseEntity<List<PlayerDto>> getPlayersByEvent(@PathVariable String eventId) {
        return ResponseEntity.ok(playerGameEventService.getPlayersEventId(eventId));
    }

    @GetMapping("/player_game_event/player")
    public ResponseEntity<List<PlayerDto>> getPlayersByGameAndEvent(@RequestParam String gameId,
                                                                    @RequestParam String eventId) {
        return ResponseEntity.ok(playerGameEventService.getPlayersByGameIdAndEventId(gameId, eventId));
    }

    @PostMapping("/player_voucher")
    public ResponseEntity<List<PlayerVoucher>> savePlayerVouchers(@RequestBody Player_VoucherQuantitiesDto dto) {
        List<PlayerVoucher> playerVouchers = playerVoucherService.addPlayerVouchers(dto);
        return ResponseEntity.ok(playerVouchers);
    }

    @PostMapping("/like")
        public ResponseEntity<Like> like(@RequestParam String userId,
                                     @RequestParam String likeableType,
                                     @RequestParam String likeableId) {
        Like newLike = likeService.like(userId, likeableType, likeableId);
        return ResponseEntity.ok(newLike);
    }

    @DeleteMapping("/player_voucher")
    public ResponseEntity<Boolean> deletePlayerVouchers(@RequestBody PlayerVouchersDto dto) {
        return ResponseEntity.ok(playerVoucherService.deletePlayerVouchers(dto));
    }
    
    @GetMapping("/player_item/player/{playerId}")
    public ResponseEntity<List<ReturnItemDto>> getItemsByPlayer(@PathVariable String playerId) {
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

    @PostMapping("/player_game_event")
    public ResponseEntity<PlayerGameEvent> savePlayerGameEvent(@RequestBody PlayerGameEventDto dto) {
        return ResponseEntity.ok(playerGameEventService.addPlayerGameEvent(dto));
    }

    @DeleteMapping("/player_item")
    public ResponseEntity<Boolean> deletePlayerItems(@RequestBody PlayerItemsDto dto) {
        return ResponseEntity.ok(playerItemService.deletePlayerItems(dto));
    }

    @DeleteMapping("/like")
    public ResponseEntity<Boolean> unlike(@RequestParam String userId,
                                          @RequestParam String likeableId,
                                          @RequestParam String likeableType) {
        likeService.unlike(userId, likeableType, likeableId);
        return ResponseEntity.ok(true);
    }
}
