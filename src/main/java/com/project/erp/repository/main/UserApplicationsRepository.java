package com.project.erp.repository.main;

import com.project.erp.entity.main.UserApplications;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserApplicationsRepository extends JpaRepository<UserApplications, Long> {

    Optional<UserApplications> findByUserIdAndJobPostingId(UUID userId, Long jobPostingId);
    // Additional custom queries can be added here
}
