package com.vou.events.repository;

import com.vou.events.entity.Item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, String>, ItemRepositoryCustom {
    List<Item> findByBrand(String brandId);
    List<Item> findByBrands(List<String> brandIds);
}