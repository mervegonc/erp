package com.project.erp.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.erp.dto.auth.company.request.EmployeeEntryRequest;
import com.project.erp.dto.auth.company.request.JobApplicationApprovalRequest;
import com.project.erp.dto.auth.company.request.UserApplicationRequest;
import com.project.erp.entity.main.UserApplications;
import com.project.erp.service.company.abstracts.CompanyService;
import com.project.erp.service.company.abstracts.EmployeeService;
import com.project.erp.service.company.abstracts.UserApplicationService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
private UserApplicationService userApplicationService;

@Autowired
private EmployeeService employeeService;
   @Autowired
    private CompanyService companyService;


@PostMapping("/apply") //işe başvuru user
public ResponseEntity<UserApplications> applyForJob(@RequestBody UserApplicationRequest userApplicationRequest) {
    // Ensure the method in the service is called correctly
    UserApplications userApplication = userApplicationService.applyForJob(userApplicationRequest);
    return ResponseEntity.ok(userApplication);
}



@PostMapping("/jobapplication/approve")
public ResponseEntity<String> approveJobApplication(@RequestBody JobApplicationApprovalRequest approvalRequest) {
    boolean isApproved = companyService.approveJobApplication(approvalRequest);

    if (isApproved) {
        return ResponseEntity.ok("Job application approved successfully.");
    } else {
        return ResponseEntity.status(400).body("Job application approval failed.");
    }
}



@PostMapping("/registerhr")
public ResponseEntity<String> registerEmployee(@RequestBody EmployeeEntryRequest employeeEntryRequest) {
    boolean isRegistered = employeeService.registerEmployee(employeeEntryRequest);

    if (isRegistered) {
        return ResponseEntity.ok("Employee registered successfully with ROLE_EMPLOYEE.");
    } else {
        return ResponseEntity.status(400).body("Employee registration failed.");
    }
}




}
