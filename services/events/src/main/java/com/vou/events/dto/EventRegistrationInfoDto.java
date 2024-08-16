package com.vou.events.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.vou.events.common.VoucherId_Quantity_ItemIds_Quantities;
import com.vou.events.common.GameId_StartTime;

import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventRegistrationInfoDto {
    private EventDto                                        event;
    private List<GameId_StartTime>                          listGameId_StartTime;
    private List<VoucherId_Quantity_ItemIds_Quantities>     listVoucher_Items;
    private List<String>                                    emails;
}