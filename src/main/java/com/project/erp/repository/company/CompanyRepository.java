package com.project.erp.repository.company;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.erp.entity.company.Company;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    // Eğer özel sorgular eklemen gerekirse buraya ekleyebilirsin
}
