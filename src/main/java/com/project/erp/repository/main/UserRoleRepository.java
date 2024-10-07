package com.project.erp.repository.main;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.erp.entity.main.UserRole;
import java.util.List;
import java.util.UUID;
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUserId(UUID userId);
}
