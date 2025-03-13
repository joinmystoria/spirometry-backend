package com.example.spirometrybackend.service.doctor;

import com.example.spirometrybackend.dto.doctor.ConsultationRequestDTO;
import com.example.spirometrybackend.dto.doctor.ConsultationResponseDTO;
import com.example.spirometrybackend.exception.ResourceNotFoundException;
import com.example.spirometrybackend.model.ConsultationEntity;
import com.example.spirometrybackend.model.DoctorEntity;
import com.example.spirometrybackend.model.UserEntity;
import com.example.spirometrybackend.repository.ConsultationRepository;
import com.example.spirometrybackend.repository.DoctorRepository;
import com.example.spirometrybackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public ConsultationResponseDTO bookConsultation(ConsultationRequestDTO requestDTO) {
        if (requestDTO.getUserId() == null || requestDTO.getDoctorId() == null) {
            throw new IllegalArgumentException("User ID and Doctor ID are required");
        }

        if (!isValidConsultationType(requestDTO.getConsultationType())) {
            throw new IllegalArgumentException("Invalid consultation type. Allowed values: Video, In-Person");
        }

        if (requestDTO.getScheduledTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Scheduled time cannot be in the past");
        }

        UserEntity user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + requestDTO.getUserId()));

        DoctorEntity doctor = doctorRepository.findById(requestDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + requestDTO.getDoctorId()));

        ConsultationEntity consultation = new ConsultationEntity();
        consultation.setUser(user);
        consultation.setDoctor(doctor);
        consultation.setConsultationType(requestDTO.getConsultationType());
        consultation.setScheduledTime(requestDTO.getScheduledTime());
        consultation.setStatus("Upcoming");

        ConsultationEntity savedConsultation = consultationRepository.save(consultation);
        return mapToResponseDTO(savedConsultation);
    }

    public List<ConsultationResponseDTO> getUserConsultations(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }

        return consultationRepository.findByUserId(userId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ConsultationResponseDTO> getDoctorConsultations(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor not found with ID: " + doctorId);
        }

        List<ConsultationEntity> consultations = consultationRepository.findByDoctorId(doctorId);

        if (consultations.isEmpty()) {
            throw new ResourceNotFoundException("No consultations found for Doctor ID: " + doctorId);
        }

        return consultations.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public void deleteConsultation(Long consultationId) {
        if (!consultationRepository.existsById(consultationId)) {
            throw new ResourceNotFoundException("Consultation not found with ID: " + consultationId);
        }
        consultationRepository.deleteById(consultationId);
    }

    private boolean isValidConsultationType(String type) {
        return "Video".equalsIgnoreCase(type) || "In-Person".equalsIgnoreCase(type);
    }

    private ConsultationResponseDTO mapToResponseDTO(ConsultationEntity consultationEntity) {
        return new ConsultationResponseDTO(
                consultationEntity.getId(),
                consultationEntity.getUser().getId(),
                consultationEntity.getDoctor().getId(),
                consultationEntity.getConsultationType(),
                consultationEntity.getScheduledTime(),
                consultationEntity.getStatus()
        );
    }
}
