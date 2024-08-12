package com.vou.users.controller;

import com.vou.users.entity.Brand;
import com.vou.users.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    private BrandService brandService;

    @Autowired
    public BrandController(BrandService theBrandService) {
        brandService = theBrandService;
    }

    @GetMapping
    public List<Brand> findAll() {
        return brandService.findAllBrands();
    }

    @GetMapping("/{brandId}")
    public Brand getBrand(@PathVariable String brandId) {
        return brandService.findBrandById(brandId);
    }

    @PostMapping
    public Brand addBrand(@RequestBody Brand theBrand) {
        theBrand.setId("0"); // to force a save of new item instead of update
        brandService.saveBrand(theBrand);
        return theBrand;
    }

    @PatchMapping
    public Brand updateBrand(@RequestBody Brand theBrand) {
        brandService.updateBrand(theBrand);
        return theBrand;
    }

    @DeleteMapping("/{brandId}")
    public String deleteBrand(@PathVariable String brandId) {
        brandService.deleteBrandById(brandId);
        return "Deleted brand id - " + brandId;
    }

    @GetMapping("/email/{email}")
    public Brand getBrandByEmail(@PathVariable String email) {
        if (email == null || email.isEmpty()) {
            throw new RuntimeException("Email is required");
        }
        // if no having any matched brand: return "No brand found with email: " + email;
        Brand result = brandService.findBrandByEmail(email);
        if (result == null) {
            throw new RuntimeException("No brand found with email: " + email);
        }
        return result;
    }

    @GetMapping("/emails")
    public List<Brand> getBrandsByEmails(@RequestBody List<String> emails) {

        if (emails == null || emails.isEmpty()) {
            throw new RuntimeException("Emails are required");
        }
        List<Brand> result = brandService.findManyBrandsByManyEmails(emails);
        if (result == null || result.isEmpty()) {
            throw new RuntimeException("No brand found with emails: " + emails);
        }
        return result;
    }


}
