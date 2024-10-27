package com.project.erp.service.company.concretes;

import com.project.erp.entity.main.Companies;
import com.project.erp.entity.main.JobPosting;
import com.project.erp.repository.main.CompaniesRepository;
import com.project.erp.repository.main.JobPostingRepository;
import com.project.erp.service.company.abstracts.JobPostingService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobPostingManager implements JobPostingService {

    @Autowired
    private JobPostingRepository jobPostingRepository;


    @Autowired
    private CompaniesRepository companiesRepository;

    @Override
public JobPosting saveJobPosting(JobPosting jobPosting) {
    // Check if the company code exists in the companies table
    Optional<Companies> companyOptional = companiesRepository.findByCompanyCode(jobPosting.getCompanyCode());
    if (companyOptional.isEmpty()) {
        throw new RuntimeException("Invalid company code. Company does not exist.");
    }
    
    // If the company exists, save the job posting
    return jobPostingRepository.save(jobPosting);
}

}
