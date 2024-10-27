package com.project.erp.dto.auth.company.response;

import com.project.erp.entity.main.JobPosting;
import com.project.erp.entity.main.UserApplications;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class UserApplicationResponse {
    private Long id;
    private UUID userId;
    private Long jobPostingId;
    private String status;
    private Timestamp createdTime;
    private Timestamp updatedTime;

    private String jobDepartment;
    private String jobPosition;
    private String jobExpectations;
    private Integer jobHiredNumber;

    public UserApplicationResponse(UserApplications userApplication) {
        this.id = userApplication.getId();
        this.userId = userApplication.getUserId();
        this.jobPostingId = userApplication.getJobPosting().getId();
        this.status = userApplication.getStatus();
        this.createdTime = userApplication.getCreatedTime();
        this.updatedTime = userApplication.getUpdatedTime();

        JobPosting jobPosting = userApplication.getJobPosting();
        this.jobDepartment = jobPosting.getDepartment();
        this.jobPosition = jobPosting.getPosition();
        this.jobExpectations = jobPosting.getExpectations();
        this.jobHiredNumber = jobPosting.getHiredNumber();
    }
}
