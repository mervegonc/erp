package com.project.erp.dto.auth.company.response;

import com.project.erp.entity.main.JobPosting;
import lombok.Data;

@Data
public class JobPostingResponse {
    private String schemaName;
    private String department;
    private String position;
    private String expectations;
    private Integer hiredNumber;

    public JobPostingResponse(JobPosting jobPosting) {
        this.schemaName = jobPosting.getSchemaName();
        this.department = jobPosting.getDepartment();
        this.position = jobPosting.getPosition();
        this.expectations = jobPosting.getExpectations();
        this.hiredNumber = jobPosting.getHiredNumber();
    }
}
