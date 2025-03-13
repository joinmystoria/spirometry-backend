package com.example.spirometrybackend.dto.doctor;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorResponseDTO {

    private Long id;

    private String name;

    private String specialization;

    private String experienceYears;

    private String profileImage;

    private boolean availableStatus;
}
