package com.project.erp.repository.main;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.erp.entity.main.UserCompany;

public interface UserCompanyRepository extends  JpaRepository<UserCompany, Long> {

    Optional<UserCompany> findByUsernameAndCompanyCode(String username, String companyCode);
    Optional<UserCompany> findByUsername(String username);
}



