package com.project.erp.dto.auth.company.request;

import lombok.Data;

import java.util.UUID;

@Data
public class UserApplicationRequest {
    private UUID userId;
    private Long jobPostingId;
}
