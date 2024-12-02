package com.teaspiritspringboot.teaspiritspringboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.teaspiritspringboot.teaspiritspringboot.model.Accessory;

public interface AccessoryRepository extends JpaRepository<Accessory, String>{

    Accessory findBySku(String sku);
    Page<Accessory> findByCategory(String category, Pageable pageable);


}