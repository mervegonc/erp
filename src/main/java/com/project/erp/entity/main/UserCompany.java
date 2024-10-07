package com.project.erp.entity.main;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_company")
public class UserCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID userId;  // Links to the user in the main schema

    @Column(nullable = false, unique = true)
    private String companyCode;  // The unique code for the company this user is associated with
}

//bu class campanyCodeları tutmak için böylelikle hesap ayrırımı yapacağız, user için /linkIN-/company account 