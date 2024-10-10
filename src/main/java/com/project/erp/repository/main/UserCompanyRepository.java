package com.project.erp.repository.main;



import org.springframework.data.jpa.repository.JpaRepository;

import com.project.erp.entity.main.UserCompany;

public interface UserCompanyRepository extends  JpaRepository<UserCompany, Long> {

  


}
