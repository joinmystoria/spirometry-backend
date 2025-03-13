package com.example.spirometrybackend.dto.doctor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ConsultationResponseDTO {

    private Long id;

    private Long userId;

    private Long doctorId;

    private String consultationType;

    private LocalDateTime scheduledTime;

    private String status; // "Upcoming", "Completed", "Canceled"
}

