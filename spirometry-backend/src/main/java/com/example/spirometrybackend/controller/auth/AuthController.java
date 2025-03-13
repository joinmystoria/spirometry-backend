package com.example.spirometrybackend.controller.auth;

import com.example.spirometrybackend.dto.auth.AuthRequestDTO;
import com.example.spirometrybackend.dto.auth.AuthResponseDTO;
import com.example.spirometrybackend.dto.auth.RegisterRequestDTO;
import com.example.spirometrybackend.dto.auth.UpdatePasswordRequestDTO;
import com.example.spirometrybackend.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordRequestDTO request) {
        return authService.updatePassword(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

}