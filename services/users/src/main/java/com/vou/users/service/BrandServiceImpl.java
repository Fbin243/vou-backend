package com.vou.users.service;

import com.vou.users.dao.BrandRepository;
import com.vou.users.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    private BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository theBrandRepository) {
        brandRepository = theBrandRepository;
    }

    @Override
    @Transactional
    public void saveBrand(Brand theBrand) {
        brandRepository.save(theBrand);
    }

    @Override
    public Brand findBrandById(String theId) {
        Optional<Brand> result = brandRepository.findById(theId);

        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RuntimeException("Did not find brand id - " + theId);
        }
    }

    @Override
    public List<Brand> findAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    @Transactional
    public void updateBrand(Brand theBrand) {
        brandRepository.save(theBrand);
    }

    @Override
    @Transactional
    public void deleteBrandById(String theId) {
        brandRepository.deleteById(theId);
    }

    @Override
    public Brand findBrandByEmail(String email) {
        return brandRepository.findByEmail(email);
    }

    @Override
    public List<Brand> findManyBrandsByManyEmails(List<String> emails) {
        return brandRepository.findManyBrandsByManyEmails(emails);
    }
}
