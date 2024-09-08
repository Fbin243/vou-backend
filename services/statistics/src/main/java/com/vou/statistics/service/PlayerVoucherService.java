package com.vou.statistics.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vou.statistics.client.EventsServiceClient;
import com.vou.statistics.client.UsersServiceClient;
import com.vou.statistics.dto.PlayerDto;
import com.vou.statistics.dto.PlayerVoucherDto;
import com.vou.statistics.dto.Player_VoucherQuantitiesDto;
import com.vou.statistics.dto.VoucherDto;
import com.vou.statistics.entity.PlayerVoucher;
import com.vou.statistics.repository.PlayerVoucherRepository;

@Service
@NoArgsConstructor
// @AllArgsConstructor
public class PlayerVoucherService implements IPlayerVoucherService {
    
    private PlayerVoucherRepository         playerVoucherRepository;
    private UsersServiceClient              usersServiceClient;
    private EventsServiceClient             eventsServiceClient;

    @Autowired
    public PlayerVoucherService(PlayerVoucherRepository playerVoucherRepository, 
                                UsersServiceClient usersServiceClient,
                                EventsServiceClient eventsServiceClient) {
        this.playerVoucherRepository = playerVoucherRepository;
        this.usersServiceClient = usersServiceClient;
        this.eventsServiceClient = eventsServiceClient;
    }

    @Override
    public List<PlayerVoucher> getByPlayerId(String playerId) {
        return playerVoucherRepository.findByPlayerId(playerId);
    }

    @Override
    public List<PlayerVoucher> getByVoucherId(String voucherId) {
        return playerVoucherRepository.findByVoucherId(voucherId);
    }

    @Override
    public List<VoucherDto> getVouchersByPlayer(String playerId) {
        List<PlayerVoucher> playerVouchers = playerVoucherRepository.findByPlayerId(playerId);
        List<VoucherDto> vouchers = eventsServiceClient.getVouchersByIds(playerVouchers.stream().map(PlayerVoucher::getVoucherId).collect(Collectors.toList()));
        return vouchers;
    }

    @Override
    public List<PlayerDto> getPlayersByVoucher(String voucherId) {
        List<PlayerVoucher> playerVouchers = playerVoucherRepository.findByVoucherId(voucherId);
        List<PlayerDto> players = usersServiceClient.getManyPlayersByManyIds(playerVouchers.stream().map(PlayerVoucher::getPlayerId).collect(Collectors.toList()));
        return players;
    }

    @Override
    public PlayerVoucher addPlayerVoucher(PlayerVoucherDto dto) {
        Optional<PlayerVoucher> existingPlayerVoucher = playerVoucherRepository.findByPlayerIdAndVoucherId(dto.getPlayerId(), dto.getVoucherId());

        if (existingPlayerVoucher.isPresent()) {
            // If the record exists, update the quantity
            PlayerVoucher playerVoucher = existingPlayerVoucher.get();
            playerVoucher.setQuantity(playerVoucher.getQuantity() + dto.getQuantity());
            return playerVoucherRepository.save(playerVoucher);
        } else {
            // If the record does not exist, create a new one
            PlayerVoucher newPlayerVoucher = new PlayerVoucher();
            newPlayerVoucher.setPlayerId(dto.getPlayerId());
            newPlayerVoucher.setVoucherId(dto.getVoucherId());
            newPlayerVoucher.setQuantity(dto.getQuantity());
            newPlayerVoucher.setBrandId(dto.getBrandId());
            newPlayerVoucher.setVoucherName(dto.getVoucherName());
            return playerVoucherRepository.save(newPlayerVoucher);
        }
    }

    @Override
    public List<PlayerVoucher> addPlayerVouchers(Player_VoucherQuantitiesDto dto) {
        return dto.getPlayerVouchers().stream().map(this::addPlayerVoucher).collect(Collectors.toList());
    }

    @Override
    public int getQuantityByPlayerIdAndVoucherId(String playerId, String voucherId) {
        Optional<PlayerVoucher> playerVoucher = playerVoucherRepository.findByPlayerIdAndVoucherId(playerId, voucherId);
        return playerVoucher.map(PlayerVoucher::getQuantity).orElse(0);
    }

    // check later
    // @Override
    // public Boolean deletePlayerVouchers(PlayerVouchersDto dto) {
    //     try {
    //         // set quantity to 0
    //         List<PlayerVoucher> playerVouchers = playerVoucherRepository.findByPlayerIdAndVoucherIds(dto.getPlayerId(), dto.getVoucherIds());
    //         playerVouchers.forEach(playerVoucher -> playerVoucher.setQuantity(0));
    //         playerVoucherRepository.saveAll(playerVouchers);
    //     }
    //     catch (Exception e) {
    //         e.printStackTrace();
    //         return false;
    //     }

    //     return true;
    // }
}
