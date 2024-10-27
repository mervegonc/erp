package com.project.erp.repository.company;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.erp.entity.company.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUserId(UUID userId);

    Optional<Employee> findByUserIdAndCompanyCode(UUID userId, String companyCode);
}
