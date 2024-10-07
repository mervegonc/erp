package com.project.erp.entity.main;
import java.sql.Timestamp;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "job_applications", schema = "main", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "job_listing_id"})
})
public class JobApplication {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "job_listing_id", nullable = false)
    private Long jobListingId;

    @Column(name = "application_date", nullable = false)
    private Timestamp applicationDate;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_time", updatable = false)
    private Timestamp createdTime;

    @Column(name = "updated_time")
    private Timestamp updatedTime;

}
