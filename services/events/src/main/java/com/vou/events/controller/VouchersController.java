package com.vou.events.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.vou.pkg.dto.ResponseDto;
import com.vou.events.common.ItemQuantity;
import com.vou.events.dto.VoucherDto;
import com.vou.events.service.IVouchersService;

import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/vouchers")
public class VouchersController {
    private final IVouchersService voucherService;

    @GetMapping
    public ResponseEntity<List<VoucherDto>> getAllVouchers() {
        List<VoucherDto> voucherDtos = voucherService.fetchAllVouchers();
        return ResponseEntity.ok(voucherDtos);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<VoucherDto> getVoucherById(@PathVariable String id) {
        VoucherDto voucherDto = voucherService.fetchVoucherById(id);
        return ResponseEntity.ok(voucherDto);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createVoucher(@RequestBody VoucherDto voucherDto) {
        voucherService.createVoucher(voucherDto);
        ResponseDto res = new ResponseDto(HttpStatus.CREATED, "Voucher created successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateVoucher(@RequestBody VoucherDto voucherDto) {
        boolean updated = voucherService.updateVoucher(voucherDto);
        if (updated) {
            ResponseDto res = new ResponseDto(HttpStatus.OK, "Voucher updated successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } else {
            ResponseDto res = new ResponseDto(HttpStatus.NOT_FOUND, "Voucher not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteVoucher(@PathVariable String id) {
        boolean deleted = voucherService.deleteVoucher(id);
        if (deleted) {
            ResponseDto res = new ResponseDto(HttpStatus.OK, "Voucher deleted successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } else {
            ResponseDto res = new ResponseDto(HttpStatus.NOT_FOUND, "Voucher not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    @PostMapping("/conversion")
    public ResponseEntity<ResponseDto> addVoucherItemConversion (@RequestParam String voucherId, @RequestParam List<ItemQuantity> itemIds_quantities) {
        voucherService.addVoucherItemConversion(voucherId, itemIds_quantities);
        ResponseDto res = new ResponseDto(HttpStatus.CREATED, "Voucher item conversion added successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
