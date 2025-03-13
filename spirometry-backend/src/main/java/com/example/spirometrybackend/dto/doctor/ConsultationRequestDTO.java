package com.example.spirometrybackend.dto.doctor;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ConsultationRequestDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @NotBlank(message = "Consultation type is required")
    private String consultationType; // "Video" or "In-Person"

    @Future(message = "Scheduled time must be in the future")
    private LocalDateTime scheduledTime;
}
