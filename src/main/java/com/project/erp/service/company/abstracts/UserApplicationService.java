package com.project.erp.service.company.abstracts;

import java.util.UUID;

import com.project.erp.dto.auth.company.request.UserApplicationRequest;
import com.project.erp.entity.main.UserApplications;

public interface UserApplicationService {
 UserApplications applyForJob(UserApplicationRequest userApplicationRequest);

}
