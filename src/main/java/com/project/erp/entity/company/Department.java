package com.project.erp.entity.company;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity  
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "department", schema = "company_schema")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Changed to Long

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "company_id")
    private UUID companyId; // Still UUID for company reference

    @Column(name = "created_time", nullable = false, updatable = false)
    private Timestamp createdTime;

    @Column(name = "updated_time")
    private Timestamp updatedTime;
}
