package com.project.erp.repository.main;

import com.project.erp.entity.main.JobPosting;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    Optional<JobPosting> findByIdAndCompanyCode(Long jobPostingId, String companyCode);
    // Additional custom queries can be added here
}
