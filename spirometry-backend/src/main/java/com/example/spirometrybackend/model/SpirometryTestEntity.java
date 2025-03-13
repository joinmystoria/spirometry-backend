package com.example.spirometrybackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "spirometry_tests")
@EntityListeners(AuditingEntityListener.class) // Ensures @CreatedDate works
public class SpirometryTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;  // Link the test to a user

    @Column(nullable = false)
    private double fev1;  // Forced Expiratory Volume in 1 second

    @Column(nullable = false)
    private double fvc;   // Forced Vital Capacity

    @Column(name = "fev1_fvc_ratio", nullable = false) // For column mapping
    private double fev1FvcRatio; // Ratio of FEV1 to FVC (renamed for consistency)

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
