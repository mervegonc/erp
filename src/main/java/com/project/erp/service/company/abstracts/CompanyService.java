package com.project.erp.service.company.abstracts;

import com.project.erp.dto.auth.company.request.CompanySignupRequest;
import com.project.erp.dto.auth.company.request.JobApplicationApprovalRequest;
import com.project.erp.dto.auth.company.response.CompanySignupResponse;
import com.project.erp.entity.main.UserApplications;

public interface CompanyService {

     CompanySignupResponse createCompanySchemaAndAddData(CompanySignupRequest companySignupRequest);
     boolean deleteSchema(String schemaName);
      boolean approveJobApplication(JobApplicationApprovalRequest approvalRequest);
     
}
