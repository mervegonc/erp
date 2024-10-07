package com.project.erp.dto.auth.company.request;

import com.project.erp.dto.auth.user.request.UserSignupRequest;
import lombok.Data;

@Data
public class FullSignupRequest {
    private CompanySignupRequest companySignupRequest;
    private UserSignupRequest userSignupRequest;
}
