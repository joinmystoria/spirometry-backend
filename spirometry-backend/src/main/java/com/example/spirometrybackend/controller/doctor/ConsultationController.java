package com.example.spirometrybackend.controller.doctor;

import com.example.spirometrybackend.dto.doctor.ConsultationRequestDTO;
import com.example.spirometrybackend.dto.doctor.ConsultationResponseDTO;
import com.example.spirometrybackend.service.doctor.ConsultationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultation")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @PostMapping("/book")
    public ResponseEntity<ConsultationResponseDTO> bookConsultation(@Valid @RequestBody ConsultationRequestDTO requestDTO) {
        return ResponseEntity.ok(consultationService.bookConsultation(requestDTO));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ConsultationResponseDTO>> getUserConsultations(@PathVariable Long userId) {
        return ResponseEntity.ok(consultationService.getUserConsultations(userId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<ConsultationResponseDTO>> getDoctorConsultations(@PathVariable Long doctorId) {
        return ResponseEntity.ok(consultationService.getDoctorConsultations(doctorId));
    }

    @DeleteMapping("/cancel/{consultationId}")
    public ResponseEntity<String> deleteConsultation(@PathVariable Long consultationId) {
        consultationService.deleteConsultation(consultationId);
        return ResponseEntity.ok("Consultation deleted successfully");
    }
}
