package com.teaspiritspringboot.teaspiritspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.teaspiritspringboot.teaspiritspringboot.model.Tea;

@Repository
public interface TeaRespository extends JpaRepository<Tea,Integer>{
    Tea findBySku(int sku);
    Tea findByNameContains(String name);

    
}
