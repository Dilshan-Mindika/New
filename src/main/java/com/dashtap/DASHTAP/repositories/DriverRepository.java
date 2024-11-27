package com.dashtap.DASHTAP.repositories;

import com.dashtap.DASHTAP.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<Driver> findByUsername(String username);

}
