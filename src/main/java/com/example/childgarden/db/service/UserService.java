package com.example.childgarden.db.service;

import com.example.childgarden.dto.request.LoginRequest;
import com.example.childgarden.dto.request.MobileRegistrationRequest;
import com.example.childgarden.dto.request.WebRegistrationRequest;
import com.example.childgarden.dto.response.AuthorizationResponse;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {

    AuthorizationResponse login(LoginRequest request);

    AuthorizationResponse mobileRegistration(MobileRegistrationRequest mobileRegistrationRequest);

    AuthorizationResponse webRegistration(WebRegistrationRequest webRegistrationRequest);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    AuthorizationResponse authWithGoogle(String tokenId) throws FirebaseAuthException;

}