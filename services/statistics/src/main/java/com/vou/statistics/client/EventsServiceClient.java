package com.vou.statistics.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.In;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vou.statistics.dto.EventVoucherAndAdditionQuantityDto;
import com.vou.statistics.dto.ItemDto;
import com.vou.statistics.dto.ReturnVoucherDto;
import com.vou.statistics.dto.VoucherDto;

@FeignClient(name = "events-service", url = "http://events:8083/events/api")
public interface EventsServiceClient {
    
    @PostMapping("/vouchers/ids")
    List<VoucherDto> getVouchersByIds(@RequestBody List<String> ids);
    
    @GetMapping("/vouchers/voucher_item/voucher/{voucherId}")
    Map<String, Integer> getItemsQuantitiesByVoucher(@PathVariable String voucherId);

    @PostMapping("/items/ids")
    List<ItemDto> getItemsByIds(@RequestBody List<String> ids);

    @PutMapping("/events/events_vouchers")
    Boolean addQuantityToEventVoucher(@RequestBody EventVoucherAndAdditionQuantityDto conversionVoucherItems);

    @GetMapping("events/quantity/{eventId}/{voucherId}")
    Integer getEventVoucherQuantity(@PathVariable String eventId, @PathVariable String voucherId);

    @GetMapping("/events/vouchers/event/{eventId}")
    List<ReturnVoucherDto> getVouchersByEvent(@PathVariable String eventId);
}