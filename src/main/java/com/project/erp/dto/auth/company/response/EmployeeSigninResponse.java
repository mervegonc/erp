package com.project.erp.dto.auth.company.response;

import java.util.UUID;

import lombok.Data;

@Data
public class EmployeeSigninResponse {
    private String token;
    private String type = "Bearer";
    private UUID userId;
    private String companyCode; // Yanıtta companyCode dönebiliriz.
    private String message;
    
    private UUID employeeId; // Giriş işlemi hakkında bilgi veren mesaj
  

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    
}
