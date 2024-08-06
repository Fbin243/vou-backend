package com.vou.users.service;

import com.vou.users.entity.Brand;
import java.util.List;

public interface BrandService {
    void saveBrand(Brand theBrand);
    Brand findBrandById(String theId);
    List<Brand> findAllBrands();
    void updateBrand(Brand theBrand);
    void deleteBrandById(String theId);
}
