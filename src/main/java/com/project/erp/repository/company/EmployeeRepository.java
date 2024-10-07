package com.project.erp.repository.company;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.erp.entity.company.Company;

public interface EmployeeRepository  extends JpaRepository<Company, UUID>{

}
