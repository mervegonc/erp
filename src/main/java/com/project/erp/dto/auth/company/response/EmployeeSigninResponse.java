package com.project.erp.dto.auth.company.response;

import java.util.UUID;

import lombok.Data;

@Data
public class EmployeeSigninResponse {
    private String token;
    private String type = "Bearer";
    private UUID userId;
    private String companyCode; // Yanıtta companyCode dönebiliriz.
    private String message;  // Giriş işlemi hakkında bilgi veren mesaj
    public void setEmployeeId(UUID employeeId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setEmployeeId'");
    }
}
