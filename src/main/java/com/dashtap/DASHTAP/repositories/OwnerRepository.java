package com.dashtap.DASHTAP.repositories;

import com.dashtap.DASHTAP.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<Owner> findByUsername(String username);

}
