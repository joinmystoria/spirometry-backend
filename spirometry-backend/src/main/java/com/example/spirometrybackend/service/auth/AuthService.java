package com.example.spirometrybackend.service.auth;

import com.example.spirometrybackend.dto.auth.AuthRequestDTO;
import com.example.spirometrybackend.dto.auth.AuthResponseDTO;
import com.example.spirometrybackend.dto.auth.RegisterRequestDTO;
import com.example.spirometrybackend.dto.auth.UpdatePasswordRequestDTO;
import com.example.spirometrybackend.exception.EmailNotFoundException;
import com.example.spirometrybackend.exception.InvalidInputException;
import com.example.spirometrybackend.exception.UserAlreadyExistsException;
import com.example.spirometrybackend.model.UserEntity;
import com.example.spirometrybackend.repository.UserRepository;
import com.example.spirometrybackend.security.JwtUtil;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Description(value = "Register a new user")
    public ResponseEntity<AuthResponseDTO> register(RegisterRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        if (!isValidPassword(request.getPassword())) {
            throw new InvalidInputException("Password must be at least 6 characters long");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword())); // Hash password before saving

        userRepository.save(userEntity);

        String token = jwtUtil.generateToken(userEntity.getEmail());
        System.out.println("Generated Token: " + token);

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @Description(value = "User Login")
    public AuthResponseDTO login(AuthRequestDTO request) {
        UserEntity userEntity = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("User not found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(userEntity.getEmail());
        System.out.println("Generated Token: " + token);

        return new AuthResponseDTO(token);
    }

    @Description(value = "Password Update")
    public ResponseEntity<String> updatePassword(UpdatePasswordRequestDTO request) {
        UserEntity userEntity = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("User not found with email: " + request.getEmail()));

        if (request.getPassword().length() < 6) {
            return ResponseEntity.badRequest().body("Password must be at least 6 characters long");
        }

        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(userEntity);

        return ResponseEntity.ok("Password updated successfully.");
    }

    // Helper method to validate password length (can be extended as needed)
    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

}
