package com.project.erp.repository.main;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.erp.entity.main.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
}