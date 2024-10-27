package com.project.erp.entity.main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies", schema = "main")
public class Companies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // user_id primary key olarak kullanılacak
 
    @Column(name = "company_code", nullable = false, unique = true)
    private String companyCode;

    @Column(name = "schema_name", nullable = false)
    private String schemaName;
}
