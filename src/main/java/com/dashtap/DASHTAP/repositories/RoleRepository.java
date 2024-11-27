package com.dashtap.DASHTAP.repositories;

import com.dashtap.DASHTAP.models.Enums.RoleEnum;
import com.dashtap.DASHTAP.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);

}
