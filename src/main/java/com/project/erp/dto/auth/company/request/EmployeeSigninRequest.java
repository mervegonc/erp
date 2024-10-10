package com.project.erp.dto.auth.company.request;


import lombok.Data;

@Data
public class EmployeeSigninRequest {
    private String usernameOrEmail;
    private String password;
    private String companyCode;
}
