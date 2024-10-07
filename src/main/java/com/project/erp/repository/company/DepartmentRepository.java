package com.project.erp.repository.company;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.erp.entity.company.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
