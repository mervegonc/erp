package com.project.erp.entity.main;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_applications", schema = "main")
public class UserApplications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "job_posting_id", referencedColumnName = "id", nullable = false)
    private JobPosting jobPosting;

    @Column(name = "status", nullable = false)
    private String status;

        @Column(name = "created_time", updatable = false)
    private Timestamp createdTime;

    @Column(name = "updated_time")
    private Timestamp updatedTime;
}
