package com.project.erp.dto.auth.company.request;

import java.util.UUID;
import java.sql.Timestamp;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeSignupRequest {

    @NotNull(message = "User ID cannot be null")
    private UUID userId;  // Main schema'daki user'a referans

    @NotNull(message = "Department ID cannot be null")
    private Long departmentId;

    @NotNull(message = "Position ID cannot be null")
    private Long positionId;

    @NotNull(message = "Company code cannot be null")
    private String companyCode;  // Hangi şirkete ait olduğunu belirtir

    @NotNull(message = "Hire date cannot be null")
    private Timestamp hireDate;
}
