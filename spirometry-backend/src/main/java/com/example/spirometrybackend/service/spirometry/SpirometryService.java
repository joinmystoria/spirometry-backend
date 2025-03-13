package com.example.spirometrybackend.service.spirometry;

import com.example.spirometrybackend.dto.spirometry.SpirometryHistoryDTO;
import com.example.spirometrybackend.dto.spirometry.StartSpirometryTestDTO;
import com.example.spirometrybackend.dto.spirometry.SubmitSpirometryTestDTO;
import com.example.spirometrybackend.dto.spirometry.SpirometryResponseDTO;
import com.example.spirometrybackend.exception.ResourceNotFoundException;
import com.example.spirometrybackend.model.SpirometryTestEntity;
import com.example.spirometrybackend.model.UserEntity;
import com.example.spirometrybackend.repository.SpirometryRepository;
import com.example.spirometrybackend.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpirometryService {

    private final SpirometryRepository spirometryRepository;
    private final UserRepository userRepository;

    public SpirometryResponseDTO startSpirometryTest(@Valid StartSpirometryTestDTO request) {
        UserEntity userEntity = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new SpirometryResponseDTO("Spirometry test started for user: " + userEntity.getEmail());
    }

    public SpirometryResponseDTO submitSpirometryTest(@Valid SubmitSpirometryTestDTO request) {
        UserEntity userEntity = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (request.getFev1() <= 0 || request.getFvc() <= 0) {
            throw new IllegalArgumentException("FEV1 and FVC values must be greater than zero");
        }

        double fev1FvcRatio = (request.getFvc() == 0) ? 0 : (request.getFev1() / request.getFvc());

        SpirometryTestEntity spirometryTestEntity = SpirometryTestEntity.builder()
                .user(userEntity)
                .fev1(request.getFev1())
                .fvc(request.getFvc())
                .fev1FvcRatio(fev1FvcRatio)
                .build();

        spirometryRepository.save(spirometryTestEntity);
        return new SpirometryResponseDTO("Spirometry test results submitted successfully");
    }

    public List<SpirometryHistoryDTO> getSpirometryHistory(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<SpirometryTestEntity> spirometryTests = spirometryRepository.findByUser(userEntity);
        return spirometryTests.stream().map(test -> new SpirometryHistoryDTO(
                test.getFev1(),
                test.getFvc(),
                test.getFev1FvcRatio(),
                test.getCreatedAt()
        )).collect(Collectors.toList());
    }
}
