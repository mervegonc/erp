package com.project.erp.dto.auth.company.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntryRequest {

    private String companyCode;
    private UUID userId;
    private Long departmentId;
    private Long positionId;
}
