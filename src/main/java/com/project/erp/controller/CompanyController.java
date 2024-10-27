package com.project.erp.controller;
import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.erp.dto.auth.company.request.JobPostingRequest;
import com.project.erp.dto.auth.company.response.JobPostingResponse;
import com.project.erp.entity.main.JobPosting;
import com.project.erp.service.company.abstracts.CompanyService;
import com.project.erp.service.company.abstracts.JobPostingService;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
 @Autowired
    private CompanyService companyService;

    @Autowired
    private JobPostingService jobPostingService;


     @DeleteMapping("/schema/{schemaName}")
    public ResponseEntity<String> deleteSchema(@PathVariable String schemaName) {
        boolean isDeleted = companyService.deleteSchema(schemaName);

        if (isDeleted) {
            return new ResponseEntity<>("Schema deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Schema not found or could not be deleted.", HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/jobposting")
public ResponseEntity<JobPostingResponse> createJobPosting(@RequestBody JobPostingRequest jobPostingRequest) {
    JobPosting jobPosting = new JobPosting();
    jobPosting.setSchemaName(jobPostingRequest.getSchemaName());
    jobPosting.setDepartment(jobPostingRequest.getDepartment());
    jobPosting.setPosition(jobPostingRequest.getPosition());
    jobPosting.setExpectations(jobPostingRequest.getExpectations());
    jobPosting.setHiredNumber(jobPostingRequest.getHiredNumber());
    jobPosting.setCompanyCode(jobPostingRequest.getCompanyCode()); // Add companyCode
    jobPosting.setCreatedTime(Timestamp.from(Instant.now()));
    jobPosting.setUpdatedTime(Timestamp.from(Instant.now()));

    JobPosting savedJobPosting = jobPostingService.saveJobPosting(jobPosting);

    return ResponseEntity.ok(new JobPostingResponse(savedJobPosting));
}




}
