package com.example.spirometrybackend.controller.spirometry;

import com.example.spirometrybackend.dto.spirometry.SpirometryHistoryDTO;
import com.example.spirometrybackend.dto.spirometry.StartSpirometryTestDTO;
import com.example.spirometrybackend.dto.spirometry.SubmitSpirometryTestDTO;
import com.example.spirometrybackend.dto.spirometry.SpirometryResponseDTO;
import com.example.spirometrybackend.service.spirometry.SpirometryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spirometry")
@RequiredArgsConstructor
public class SpirometryController {

    private final SpirometryService spirometryService;

    @PostMapping("/start")
    public ResponseEntity<SpirometryResponseDTO> startSpirometryTest(@Valid @RequestBody StartSpirometryTestDTO request) {
        return ResponseEntity.ok(spirometryService.startSpirometryTest(request));
    }

    @PostMapping("/submit")
    public ResponseEntity<SpirometryResponseDTO> submitSpirometryTest(@Valid @RequestBody SubmitSpirometryTestDTO request) {
        return ResponseEntity.ok(spirometryService.submitSpirometryTest(request));
    }

    @GetMapping("/history")
    public ResponseEntity<List<SpirometryHistoryDTO>> getSpirometryHistory(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(spirometryService.getSpirometryHistory(userDetails.getUsername()));
    }
}
