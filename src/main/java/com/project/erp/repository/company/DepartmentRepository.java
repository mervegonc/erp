package com.project.erp.repository.company;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.erp.entity.company.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByNameAndCompanyId(String name, UUID companyId);



}
