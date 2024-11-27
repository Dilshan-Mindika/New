package com.dashtap.DASHTAP.repositories;

import com.dashtap.DASHTAP.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    void deleteByName(String name);

    Brand findByName(String name);

}
