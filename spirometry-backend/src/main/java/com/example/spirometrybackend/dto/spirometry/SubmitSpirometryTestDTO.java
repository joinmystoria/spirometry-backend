package com.example.spirometrybackend.dto.spirometry;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitSpirometryTestDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Min(value = 0, message = "FEV1 must be greater than zero")
    private double fev1;

    @Min(value = 0, message = "FVC must be greater than zero")
    private double fvc;
}
