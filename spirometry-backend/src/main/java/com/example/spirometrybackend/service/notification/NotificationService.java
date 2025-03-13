package com.example.spirometrybackend.service.notification;

import com.example.spirometrybackend.dto.notification.NotificationRequestDTO;
import com.example.spirometrybackend.dto.notification.NotificationResponseDTO;
import com.example.spirometrybackend.exception.ResourceNotFoundException;
import com.example.spirometrybackend.model.NotificationEntity;
import com.example.spirometrybackend.model.UserEntity;
import com.example.spirometrybackend.repository.NotificationRepository;
import com.example.spirometrybackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    // Create a new notification
    public NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO) {
        UserEntity userEntity = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + requestDTO.getUserId()));

        NotificationEntity notification = new NotificationEntity();
        notification.setUser(userEntity);
        notification.setMessage(requestDTO.getMessage());
        notification.setRead(requestDTO.isRead());

        NotificationEntity savedNotification = notificationRepository.save(notification);
        return mapToResponseDTO(savedNotification);
    }

    // Get all notifications for a user
    public List<NotificationResponseDTO> getUserNotifications(Long userId) {
        List<NotificationEntity> notifications = notificationRepository.findByUserId(userId);

        if (notifications.isEmpty()) {
            throw new ResourceNotFoundException("No notifications found for user ID: " + userId);
        }

        return notifications.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    // Mark a notification as read
    @Transactional
    public NotificationResponseDTO markAsRead(Long notificationId) {
        NotificationEntity notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + notificationId));

        notification.setRead(true);
        return mapToResponseDTO(notification);
    }

    // Delete a notification
    @Transactional
    public void deleteNotification(Long notificationId) {
        if (!notificationRepository.existsById(notificationId)) {
            throw new ResourceNotFoundException("Notification not found with ID: " + notificationId);
        }
        notificationRepository.deleteById(notificationId);
    }

    // Helper method to map entity to DTO
    private NotificationResponseDTO mapToResponseDTO(NotificationEntity notification) {
        return new NotificationResponseDTO(
                notification.getId(),
                notification.getUser().getId(),
                notification.getMessage(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }
}
