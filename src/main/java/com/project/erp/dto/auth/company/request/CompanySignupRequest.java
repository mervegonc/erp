package com.project.erp.dto.auth.company.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanySignupRequest {
    
    private String schemaName;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String companyCode;

    @NotBlank
    private String adminUsername;

    @NotBlank
    @Email
    private String adminEmail;

    @NotBlank
    private String adminPassword;
}
