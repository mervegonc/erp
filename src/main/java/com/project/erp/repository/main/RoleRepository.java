package com.project.erp.repository.main;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.erp.entity.main.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
