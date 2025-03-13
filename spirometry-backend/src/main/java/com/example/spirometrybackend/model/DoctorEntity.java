package com.example.spirometrybackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.net.URL;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String specialization;

    private String experienceYears;

    private String profileImage; // URL to the doctor's image

    private boolean availableStatus; // If the doctor is available for consultation
}
