package com.example.childgarden.apies;

import com.example.childgarden.db.service.UserService;
import com.example.childgarden.db.service.serviceimpl.UserServiceImpl;
import com.example.childgarden.dto.request.LoginRequest;
import com.example.childgarden.dto.request.MobileRegistrationRequest;
import com.example.childgarden.dto.request.WebRegistrationRequest;
import com.example.childgarden.dto.response.AuthorizationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@Tag(name = "Auth API", description = "Authentication API")
public class AuthApi {

    private final UserService userService;

    private final UserServiceImpl authService;

    @PostMapping("/login")
    @Operation(summary = "Sign in", description = "Any user can authenticate")
    public AuthorizationResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @PostMapping("/registration/mobile")
    @Operation(summary = "Sign up mobile", description = "This is mobile registration. Roles: PARENT, TEACHER, CHILD_GARDEN")
    public AuthorizationResponse mobileRegistration(@RequestBody MobileRegistrationRequest signupRequest) {
        return authService.mobileRegistration(signupRequest);
    }

    @PostMapping("/registration/web")
    @Operation(summary = "Sign up web", description = "This is web registration. Roles: PARENT, TEACHER, CHILD_GARDEN")
    public AuthorizationResponse webRegistration(@RequestBody WebRegistrationRequest webRegistrationRequest) {
        return authService.webRegistration(webRegistrationRequest);
    }

    @PostMapping("/google")
    @Operation(summary = "Sign up & sign in", description = "Authenticate via Google")
    public AuthorizationResponse authResponse(@RequestParam String tokenId) {
        return authService.authWithGoogle(tokenId);
    }
}