package com.project.erp.service.company.abstracts;

import com.project.erp.dto.auth.company.request.CompanySignupRequest;
import com.project.erp.dto.auth.company.response.CompanySignupResponse;

public interface CompanyService {

     CompanySignupResponse createCompanySchemaAndAddData(CompanySignupRequest companySignupRequest);
}
