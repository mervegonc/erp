package com.project.erp.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDetailUpdateRequest {

    @NotNull(message = "User ID cannot be null")
    private UUID userId;  // Include the userId to map the relationship between User and UserDetail

    @NotNull(message = "Education cannot be null")
    private String education;

    private String homeAddress;
    private String previousExperience;
    private String phoneNumber;
    private String linkedinProfile;
    private String skills;
    private String maritalStatus;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private LocalDate dateOfBirth;
    private String nationality;
}
