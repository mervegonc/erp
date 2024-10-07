package com.project.erp.repository.main;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.erp.entity.main.UserDetail;

import java.util.Optional;
import java.util.UUID;

public interface UserDetailRepository extends JpaRepository<UserDetail, UUID> {


    Optional<UserDetail> findById(UUID id);

    Optional<UserDetail> findByUserId(UUID userId);

    

}
