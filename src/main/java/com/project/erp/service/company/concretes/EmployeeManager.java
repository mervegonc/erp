package com.project.erp.service.company.concretes;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.project.erp.dto.auth.company.request.EmployeeEntryRequest;
import com.project.erp.dto.auth.company.request.EmployeeSigninRequest;
import com.project.erp.dto.auth.company.request.JobApplicationApprovalRequest;
import com.project.erp.entity.company.Employee;
import com.project.erp.entity.main.Companies;
import com.project.erp.entity.main.JobPosting;
import com.project.erp.entity.main.User;
import com.project.erp.entity.main.UserCompany;
import com.project.erp.jwt.JwtTokenProvider;
import com.project.erp.repository.company.EmployeeRepository;
import com.project.erp.repository.main.CompaniesRepository;
import com.project.erp.repository.main.JobPostingRepository;
import com.project.erp.repository.main.UserCompanyRepository;
import com.project.erp.repository.main.UserRepository;
import com.project.erp.service.company.abstracts.EmployeeService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;



@Service
public class EmployeeManager implements EmployeeService {
  @Autowired
    private final UserCompanyRepository userCompanyRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;
      @Autowired
    private EntityManager entityManager; 
    @Autowired
    private CompaniesRepository companiesRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    JobPostingRepository jobPostingRepository;

    public EmployeeManager(UserCompanyRepository userCompanyRepository, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userCompanyRepository = userCompanyRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(EmployeeSigninRequest employeeSigninRequest) {
        // Authentication işlemi
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(employeeSigninRequest.getUsernameOrEmail(), employeeSigninRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // UserCompany kontrolü: companyCode ve status ACTIVE mi?
        UserCompany userCompany = userCompanyRepository
            .findByUsernameAndCompanyCode(employeeSigninRequest.getUsernameOrEmail(), employeeSigninRequest.getCompanyCode())
            .orElseThrow(() -> new RuntimeException("User not associated with this company or company not found"));

        if (!userCompany.getStatus().equals("ACTIVE")) {
            throw new RuntimeException("Company status is not ACTIVE");
        }

        // Token oluşturulması
        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public UUID getEmployeeIdByUsername(String usernameOrEmail) {
        // Employee ID'yi UserCompany tablosundan alıyoruz
        return userCompanyRepository.findByUsername(usernameOrEmail)
            .map(UserCompany::getUserId)
            .orElseThrow(() -> new RuntimeException("User not found with username: " + usernameOrEmail));
    }
    
    @Override
    public UserCompany getUserCompanyByUsernameAndCompanyCode(String usernameOrEmail, String companyCode) {
        // UserCompany kaydını username ve companyCode ile buluyoruz
        return userCompanyRepository.findByUsernameAndCompanyCode(usernameOrEmail, companyCode)
            .orElseThrow(() -> new RuntimeException("User not associated with this company or company not found"));
    }

    @Override
    @Transactional
    public boolean registerEmployee(EmployeeEntryRequest employeeEntryRequest) {
        // 1. Ana şemadaki users tablosunda userId var mı kontrol et
        Optional<User> user = userRepository.findById(employeeEntryRequest.getUserId());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User does not exist in main schema.");
        }
    
        // 2. Şirketin ana şemada olup olmadığını company_code ile kontrol et
        Optional<Companies> company = companiesRepository.findByCompanyCode(employeeEntryRequest.getCompanyCode());
        if (company.isEmpty()) {
            throw new IllegalArgumentException("Company code does not exist.");
        }
    
        // 3. Şirketin şema adını al ve employee tablosuna kayıt yap
        String schemaName = company.get().getSchemaName();
    
        String insertEmployeeQuery = "INSERT INTO " + schemaName + ".employee " +
            "(id, user_id, department_id, position_id, status, hire_date, company_code, created_time) " +
            "VALUES (:id, :userId, :departmentId, :positionId, 'ACTIVE', :hireDate, :companyCode, :createdTime)";
        
        UUID employeeId = UUID.randomUUID();
        entityManager.createNativeQuery(insertEmployeeQuery)
            .setParameter("id", employeeId)
            .setParameter("userId", employeeEntryRequest.getUserId())
            .setParameter("departmentId", employeeEntryRequest.getDepartmentId())
            .setParameter("positionId", employeeEntryRequest.getPositionId())
            .setParameter("hireDate", new Timestamp(System.currentTimeMillis()))
            .setParameter("companyCode", employeeEntryRequest.getCompanyCode())
            .setParameter("createdTime", new Timestamp(System.currentTimeMillis()))
            .executeUpdate();
    
        // 4. employee_role tablosuna ROLE_EMPLOYEE olarak kayıt yap
        String insertEmployeeRoleQuery = "INSERT INTO " + schemaName + ".employee_role " +
            "(employee_id, role_id, assigned_date, created_time) " +
            "VALUES (:employeeId, (SELECT id FROM " + schemaName + ".e_role WHERE name = 'ROLE_EMPLOYEE'), :assignedDate, :createdTime)";
        
        entityManager.createNativeQuery(insertEmployeeRoleQuery)
            .setParameter("employeeId", employeeId)
            .setParameter("assignedDate", new Timestamp(System.currentTimeMillis()))
            .setParameter("createdTime", new Timestamp(System.currentTimeMillis()))
            .executeUpdate();
    
        return true;
    }
    

    
}