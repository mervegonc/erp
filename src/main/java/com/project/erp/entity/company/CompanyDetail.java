package com.project.erp.entity.company;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company_details", schema = "company_schema")
public class CompanyDetail {

    
    @Id
@GeneratedValue(strategy = GenerationType.AUTO)
private UUID id;


@OneToOne
@JoinColumn(name = "company_id", referencedColumnName = "id")
private Company company;


    @Column(name = "additional_address", length = 255)
    private String additionalAddress;

    @Column(name = "legal_status", length = 100)
    private String legalStatus;

    @Column(name = "annual_revenue", precision = 15, scale = 2)
    private BigDecimal annualRevenue;

    @Column(name = "number_of_offices")
    private int numberOfOffices;

    @Column(name = "company_size", length = 50)
    private String companySize;

    @Column(name = "industry", length = 100)
    private String industry;


    @Column(name = "founded_date")
    private LocalDate foundedDate;


    @Column(name = "ceo_name", length = 100)
    private String ceoName;

    @Column(name = "business_registration_number", length = 50)
    private String businessRegistrationNumber;

    @Column(name = "annual_budget", precision = 15, scale = 2)
    private BigDecimal annualBudget;

}

