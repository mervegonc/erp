package com.project.erp.entity.company;

import java.util.UUID;
import java.sql.Timestamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee", schema = "company_schema")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID userId;  // Links to the user in the main schema

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @Column(nullable = false)
    private String status ;  
    @Column(name = "company_code", nullable = false)  // Bu alanı ekleyin
    private String companyCode;

    @Column(name = "hire_date", nullable = false)
    private Timestamp hireDate;

    @Column(name = "created_time", nullable = false, updatable = false)
    private Timestamp createdTime;

    @Column(name = "updated_time")
    private Timestamp updatedTime;
}