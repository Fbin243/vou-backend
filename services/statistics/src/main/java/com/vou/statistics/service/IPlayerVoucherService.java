package com.vou.statistics.service;

import java.util.List;

import com.vou.statistics.dto.PlayerDto;
import com.vou.statistics.dto.PlayerVoucherDto;
import com.vou.statistics.dto.PlayerVouchersDto;
import com.vou.statistics.dto.Player_VoucherQuantitiesDto;
import com.vou.statistics.dto.VoucherDto;
import com.vou.statistics.entity.PlayerVoucher;

/**
 * Service interface for managing playervouchers.
 */
public interface IPlayerVoucherService {
    
    /**
     * Get all player vouchers by player id.
     * 
     * @param playerId the player id
     * @return the list of player vouchers
     */
    default List<PlayerVoucher> getByPlayerId(String playerId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Get all player vouchers by voucher id.
     * 
     * @param voucherId the voucher id
     * @return the list of player vouchers
     */
    default List<PlayerVoucher> getByVoucherId(String voucherId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Get all vouchers by player id.
     * 
     * @param playerId the player id
     * @return the list of vouchers
     */
    default List<VoucherDto> getVouchersByPlayer(String playerId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Get all players by voucher id.
     * 
     * @param voucherId the voucher id
     * @return the list of players
     */
    default List<PlayerDto> getPlayersByVoucher(String voucherId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Add player voucher.
     * 
     * @param dto the player voucher dto
     * @return the player voucher
     */
    default PlayerVoucher addPlayerVoucher(PlayerVoucherDto dto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Add player vouchers.
     * 
     * @param dto the player voucher quantities dto
     * @return the list of player vouchers
     */
    default List<PlayerVoucher> addPlayerVouchers(Player_VoucherQuantitiesDto dto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Delete player voucher.
     * 
     * @param dto the player voucher dto
     * @return the boolean
     */
    default Boolean deletePlayerVouchers(PlayerVouchersDto dto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
