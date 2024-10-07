package com.project.erp.entity.company;

import java.sql.Timestamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "e_role", schema = "company_schema") 
public class ERole {

    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


    @Column(nullable = false, unique = true)
    private String name;  // Example: ROLE_ADMIN, ROLE_MANAGER, ROLE_EMPLOYEE

    @Column(name = "created_time", nullable = false, updatable = false)
    private Timestamp createdTime;

    @Column(name = "updated_time")
    private Timestamp updatedTime;





    
}
