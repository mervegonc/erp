package com.project.erp.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.erp.dto.auth.company.request.CompanySignupRequest;
import com.project.erp.dto.auth.company.request.EmployeeSigninRequest;
import com.project.erp.dto.auth.company.response.CompanySignupResponse;
import com.project.erp.dto.auth.company.response.EmployeeSigninResponse;
import com.project.erp.dto.auth.user.request.UserSigninRequest;
import com.project.erp.dto.auth.user.request.UserSignupRequest;
import com.project.erp.dto.auth.user.response.UserSigninResponse;
import com.project.erp.dto.auth.user.response.UserSignupResponse;
import com.project.erp.entity.main.UserCompany;
import com.project.erp.service.company.abstracts.CompanyService;
import com.project.erp.service.company.abstracts.EmployeeService;
import com.project.erp.service.main.abstracts.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final EmployeeService employeeService;
	private final UserService userService;
	 @Autowired
    private CompanyService companyService;

	@PostMapping("/signin")
	public ResponseEntity<UserSigninResponse> login(@RequestBody UserSigninRequest userSigninRequest) {
		String token = userService.login(userSigninRequest);
		System.out.println(userSigninRequest.getPassword());
		UUID userId = userService.getUserIdByUsername(userSigninRequest.getUsernameOrEmail());
		UserSigninResponse userSigninResponse = new UserSigninResponse();
		userSigninResponse.setToken(token);
		userSigninResponse.setUserId(userId);
		return new ResponseEntity<>(userSigninResponse, HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<UserSignupResponse> signup(@RequestBody UserSignupRequest userSignupRequest) {
		boolean isUserExist = userService.isUserExist(userSignupRequest.getUsername());

		if (isUserExist) {
			UserSignupResponse response = new UserSignupResponse();
			response.setMessage("User already exists");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

		userService.signupAndAssignRole(userSignupRequest, "ROLE_USER");

		UserSignupResponse userSignupResponse = new UserSignupResponse();
		userSignupResponse.setMessage("User registered successfully!");
		return new ResponseEntity<>(userSignupResponse, HttpStatus.CREATED);
	}


@PostMapping("/application")
public ResponseEntity<CompanySignupResponse> createCompanySchemaAndAddData(@Valid @RequestBody CompanySignupRequest companySignupRequest) {
    CompanySignupResponse response = companyService.createCompanySchemaAndAddData(companySignupRequest);
    return ResponseEntity.ok(response);
}
/* 
@PostMapping("/signin/employee")
public ResponseEntity<EmployeeSigninResponse> loginEmployee(@RequestBody EmployeeSigninRequest employeeSigninRequest) {
	// Employee login işlemi
	String token = employeeService.login(employeeSigninRequest);
	
	// Employee ID alma işlemi
	UUID employeeId = employeeService.getEmployeeIdByUsername(employeeSigninRequest.getUsernameOrEmail());

	// EmployeeSigninResponse objesi oluşturma
	EmployeeSigninResponse employeeSigninResponse = new EmployeeSigninResponse();
	employeeSigninResponse.setToken(token);
	employeeSigninResponse.setEmployeeId(employeeId);

	// Yanıt döndürme
	return new ResponseEntity<>(employeeSigninResponse, HttpStatus.OK);
}*/


@PostMapping("/signin/employee")
public ResponseEntity<EmployeeSigninResponse> loginEmployee(@RequestBody EmployeeSigninRequest employeeSigninRequest) {
    // Employee login işlemi
    String token = employeeService.login(employeeSigninRequest);

    // Employee ID ve UserCompany bilgilerini alıyoruz
    UUID employeeId = employeeService.getEmployeeIdByUsername(employeeSigninRequest.getUsernameOrEmail());
    UserCompany userCompany = employeeService.getUserCompanyByUsernameAndCompanyCode(employeeSigninRequest.getUsernameOrEmail(), employeeSigninRequest.getCompanyCode());

    // EmployeeSigninResponse objesi oluşturma
    EmployeeSigninResponse employeeSigninResponse = new EmployeeSigninResponse();
    employeeSigninResponse.setToken(token);
    employeeSigninResponse.setEmployeeId(employeeId);
    employeeSigninResponse.setUserId(userCompany.getUserId());
    employeeSigninResponse.setCompanyCode(userCompany.getCompanyCode());
    employeeSigninResponse.setMessage("Login successful");

    // Yanıt döndürme
    return new ResponseEntity<>(employeeSigninResponse, HttpStatus.OK);
}


}
