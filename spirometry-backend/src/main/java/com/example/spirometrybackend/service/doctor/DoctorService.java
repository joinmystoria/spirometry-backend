package com.example.spirometrybackend.service.doctor;

import com.example.spirometrybackend.dto.doctor.DoctorRequestDTO;
import com.example.spirometrybackend.dto.doctor.DoctorResponseDTO;
import com.example.spirometrybackend.exception.ResourceNotFoundException;
import com.example.spirometrybackend.model.DoctorEntity;
import com.example.spirometrybackend.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorResponseDTO addDoctor(DoctorRequestDTO requestDTO) {
        DoctorEntity doctorEntity = DoctorEntity.builder()
                .name(requestDTO.getName())
                .specialization(requestDTO.getSpecialization())
                .experienceYears(requestDTO.getExperienceYears())
                .profileImage(requestDTO.getProfileImage())
                .availableStatus(requestDTO.isAvailableStatus())
                .build();

        doctorRepository.save(doctorEntity);
        return mapToResponseDTO(doctorEntity);
    }

    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<DoctorResponseDTO> getAvailableDoctors() {
        return doctorRepository.findByAvailableStatusTrue().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public DoctorResponseDTO getDoctorById(Long id) {
        DoctorEntity doctorEntity = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + id));
        return mapToResponseDTO(doctorEntity);
    }

    public DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO requestDTO) {
        DoctorEntity doctorEntity = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + id));

        doctorEntity.setName(requestDTO.getName());
        doctorEntity.setSpecialization(requestDTO.getSpecialization());
        doctorEntity.setExperienceYears(requestDTO.getExperienceYears());
        doctorEntity.setProfileImage(requestDTO.getProfileImage());
        doctorEntity.setAvailableStatus(requestDTO.isAvailableStatus());

        doctorRepository.save(doctorEntity);
        return mapToResponseDTO(doctorEntity);
    }

    @Transactional
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with ID: " + id);
        }
        doctorRepository.deleteById(id);
    }

    private DoctorResponseDTO mapToResponseDTO(DoctorEntity doctorEntity) {
        return new DoctorResponseDTO(
                doctorEntity.getId(),
                doctorEntity.getName(),
                doctorEntity.getSpecialization(),
                doctorEntity.getExperienceYears(),
                doctorEntity.getProfileImage(),
                doctorEntity.isAvailableStatus()
        );
    }
}
