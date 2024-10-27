package com.project.erp.service.company.abstracts;

import java.util.UUID;

import com.project.erp.dto.auth.company.request.EmployeeEntryRequest;
import com.project.erp.dto.auth.company.request.EmployeeSigninRequest;
import com.project.erp.dto.auth.company.request.JobApplicationApprovalRequest;
import com.project.erp.entity.main.UserCompany;

public interface EmployeeService {
  /*String login(EmployeeSigninRequest employeeSigninRequest);
    UUID getEmployeeIdByUsername(String usernameOrEmail);*/

     String login(EmployeeSigninRequest employeeSigninRequest);
    
    UUID getEmployeeIdByUsername(String usernameOrEmail);
    
    UserCompany getUserCompanyByUsernameAndCompanyCode(String usernameOrEmail, String companyCode);
    boolean registerEmployee(EmployeeEntryRequest employeeEntryRequest);


}
