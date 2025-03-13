package com.example.spirometrybackend.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class NotificationResponseDTO {

    private Long id;

    private Long userId;

    private String message;

    private boolean isRead;

    private LocalDateTime createdAt;
}
