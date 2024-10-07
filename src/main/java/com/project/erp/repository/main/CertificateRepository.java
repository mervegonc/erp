package com.project.erp.repository.main;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.erp.entity.main.Certificate;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
