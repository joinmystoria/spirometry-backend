package com.example.spirometrybackend.controller.user;

import com.example.spirometrybackend.dto.user.UserProfileRequestDTO;
import com.example.spirometrybackend.dto.user.UserProfileResponseDTO;
import com.example.spirometrybackend.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponseDTO> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getUserProfile(userDetails.getUsername()));
    }

    @PutMapping("/update/profile")
    public ResponseEntity<String> updateUserProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UserProfileRequestDTO userProfileRequestDTO) {

        return ResponseEntity.ok(userService.updateUserProfile(userDetails.getUsername(), userProfileRequestDTO));
    }
}
