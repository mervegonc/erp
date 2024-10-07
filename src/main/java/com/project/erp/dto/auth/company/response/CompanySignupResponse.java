package com.project.erp.dto.auth.company.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanySignupResponse {
    
    private String id;          // Şirketin UUID'si
    private String name;        // Şirketin ismi
    private String message;     // Başarı mesajı

}
