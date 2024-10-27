package com.project.erp.repository.main;

import com.project.erp.entity.main.Companies;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompaniesRepository extends JpaRepository<Companies, Long> {
    boolean existsByCompanyCode(String companyCode);
    Optional<Companies> findByCompanyCode(String companyCode);
}
