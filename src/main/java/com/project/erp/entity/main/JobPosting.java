package com.project.erp.entity.main;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_posting", schema = "main")
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "schema_name", nullable = false)
    private String schemaName;

    @Column(name = "company_code", nullable = false)
    private String companyCode; 

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "hired_number", nullable = false)
    private Integer hiredNumber;

    @Column(name = "expectations", nullable = false)
    private String expectations;
    

    @Column(name = "created_time", updatable = false)
    private Timestamp createdTime;

    @Column(name = "updated_time")
    private Timestamp updatedTime;

}
