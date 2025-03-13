package com.example.spirometrybackend.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileRequestDTO {

    private String firstName;

    private String lastName;

    @NotNull(message = "Email is required")
    private String email;

    private String phoneNumber;

    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private LocalDate birthDate;

    private String gender;

    private BigDecimal height;

    private BigDecimal weight;

}
