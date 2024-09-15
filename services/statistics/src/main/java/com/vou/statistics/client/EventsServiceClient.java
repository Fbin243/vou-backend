package com.vou.statistics.client;

import com.vou.statistics.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
	
	@GetMapping("/events/id/{id}")
	EventDto getEventById(@PathVariable String id);
}
