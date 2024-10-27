package com.project.erp.service.company.concretes;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.erp.dto.auth.company.request.UserApplicationRequest;
import com.project.erp.entity.company.Employee;
import com.project.erp.entity.main.JobPosting;
import com.project.erp.entity.main.User;
import com.project.erp.entity.main.UserApplications;
import com.project.erp.repository.company.EmployeeRepository;
import com.project.erp.repository.main.JobPostingRepository;
import com.project.erp.repository.main.UserApplicationsRepository;
import com.project.erp.repository.main.UserRepository;
import com.project.erp.service.company.abstracts.UserApplicationService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class UserApplicationManager implements UserApplicationService {
    @Autowired
    private UserApplicationsRepository userApplicationsRepository;

    @Autowired
    private JobPostingRepository jobPostingRepository;

     @Autowired
    private EmployeeRepository employeeRepository;



    @Autowired
    private UserRepository usersRepository; // Repository for checking if a user exists

    @Autowired
    private EntityManager entityManager;
    


  @Override
    @Transactional
    public UserApplications applyForJob(UserApplicationRequest userApplicationRequest) {
        // 1. Check if the user exists in the 'users' table
        Optional<User> userOpt = usersRepository.findById(userApplicationRequest.getUserId());
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User does not exist.");
        }

        // 2. Check if the job posting exists
        Optional<JobPosting> jobPostingOpt = jobPostingRepository.findById(userApplicationRequest.getJobPostingId());
        if (jobPostingOpt.isEmpty()) {
            throw new IllegalArgumentException("Job posting does not exist.");
        }

        // 3. Create a new user application record
        UserApplications userApplication = new UserApplications();
        userApplication.setUserId(userApplicationRequest.getUserId());
        userApplication.setJobPosting(jobPostingOpt.get());
        userApplication.setStatus("NOT_APPROVED");// Status initially set to 'PASSIVE'
        userApplication.setCreatedTime(Timestamp.from(Instant.now()));
        userApplication.setUpdatedTime(Timestamp.from(Instant.now()));

        // 4. Save the application to the database
        return userApplicationsRepository.save(userApplication);
    }



}