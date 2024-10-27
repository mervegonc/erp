package com.project.erp.dto.auth.company.request;


import java.util.UUID;
import lombok.Data;

@Data
public class JobApplicationApprovalRequest {
    private String companyCode;
    private UUID userId; // İş başvurusunu onaylayacağımız kişi
    private Long jobPostingId; // Başvurulan iş pozisyonunun ID'si
}

