package com.example.spirometrybackend.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserProfileResponseDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private LocalDate birthDate;

    private String gender;

    private BigDecimal height;

    private BigDecimal weight;
}
