package com.project.erp.dto.auth.company.response;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class EmployeeSignupResponse {

    private Long employeeId;  // Çalışan kaydının id'si
    private String message;   // Başarılı/başarısız mesajı
    private Timestamp createdTime;  // Kayıt oluşturulma zamanı
}
