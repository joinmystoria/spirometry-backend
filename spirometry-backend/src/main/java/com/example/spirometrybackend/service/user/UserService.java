package com.example.spirometrybackend.service.user;

import com.example.spirometrybackend.dto.user.UserProfileRequestDTO;
import com.example.spirometrybackend.dto.user.UserProfileResponseDTO;
import com.example.spirometrybackend.exception.ResourceNotFoundException;
import com.example.spirometrybackend.model.UserEntity;
import com.example.spirometrybackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserProfileResponseDTO getUserProfile(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return mapToResponseDTO(userEntity);
    }

    @Transactional
    public String updateUserProfile(String email, UserProfileRequestDTO requestDTO) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userEntity.setFirstName(requestDTO.getFirstName());
        userEntity.setLastName(requestDTO.getLastName());
        userEntity.setEmail(requestDTO.getEmail());

        // Validate phone number (optional)
        if (requestDTO.getPhoneNumber() != null && !isValidPhone(requestDTO.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        userEntity.setPhoneNumber(requestDTO.getPhoneNumber());
        userEntity.setAddress(requestDTO.getAddress());
        userEntity.setBirthDate(requestDTO.getBirthDate());
        userEntity.setGender(requestDTO.getGender());
        userEntity.setHeight(requestDTO.getHeight());
        userEntity.setWeight(requestDTO.getWeight());

        userRepository.save(userEntity);

        return String.valueOf("Profile update is successful") ;
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("\\+?[0-9]{10,15}"); // Example: "+1234567890" or "1234567890"
    }

    private UserProfileResponseDTO mapToResponseDTO(UserEntity userEntity) {
        return new UserProfileResponseDTO(
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getPhoneNumber(),
                userEntity.getAddress(),
                userEntity.getBirthDate(),
                userEntity.getGender(),
                userEntity.getHeight(),
                userEntity.getWeight()
        );
    }
}
