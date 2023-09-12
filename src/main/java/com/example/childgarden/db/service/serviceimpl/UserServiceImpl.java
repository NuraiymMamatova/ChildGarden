package com.example.childgarden.db.service.serviceimpl;

import com.example.childgarden.config.jwt.JwtTokenUtil;
import com.example.childgarden.db.entities.Role;
import com.example.childgarden.db.entities.User;
import com.example.childgarden.db.repository.RoleRepository;
import com.example.childgarden.db.repository.UserRepository;
import com.example.childgarden.db.service.UserService;
import com.example.childgarden.dto.request.LoginRequest;
import com.example.childgarden.dto.request.MobileRegistrationRequest;
import com.example.childgarden.dto.request.WebRegistrationRequest;
import com.example.childgarden.dto.response.AuthorizationResponse;
import com.example.childgarden.exceptions.ExceptionResponse;
import com.example.childgarden.exceptions.NotFoundException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    private final RoleRepository roleRepository;

    @PostConstruct
    void init() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource("child-garden.json").getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions.builder().setCredentials(googleCredentials).build();
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
        }
    }

    @Override
    public AuthorizationResponse authWithGoogle(String tokenId) {
        FirebaseToken firebaseToken;
        try {
            firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenId);
        } catch (FirebaseAuthException firebaseAuthException) {
            log.error("");
            throw new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, firebaseAuthException.getClass().getSimpleName(), firebaseAuthException.getMessage());
        }

        User user;
        if (userRepository.findByEmail(firebaseToken.getEmail()).isEmpty()) {
            user = new User();
            Role role = roleRepository.findByName("USER");
            role.addUser(user);
            user.addRole(role);
            user.setPassword(passwordEncoder.encode(firebaseToken.getEmail()));
            user.setName(firebaseToken.getName());
            user.setEmail(firebaseToken.getEmail());
            user.setImage(firebaseToken.getPicture());
            roleRepository.save(role);
        }

        user = userRepository.findByEmail(firebaseToken.getEmail()).orElseThrow(() -> new NotFoundException(String.format("User %s not found!", firebaseToken.getEmail())));
        log.error("User %s not found!", firebaseToken.getEmail());
        String token = jwtTokenUtil.generateToken(user);
        return new AuthorizationResponse(token, user.getEmail(), userRepository.findRoleByUserEmail(user.getEmail()).getRoleName());
    }

    @Override
    public AuthorizationResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        User user = userRepository.findByEmail(token.getName()).orElseThrow(() -> {
            throw new NotFoundException("the user with this email was not found");
        });
        if (request.getPassword() == null) {
            log.error("The email {} password not found", user.getEmail());
            throw new NotFoundException("Password must not be empty");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.error("The user {} invalid password", user.getEmail());
            throw new BadCredentialsException("invalid password");
        }
        return new AuthorizationResponse(jwtTokenUtil.generateToken(user), user.getEmail(), roleRepository.findRoleByUserId(user.getId()).getRoleName());
    }

    @Override
    public AuthorizationResponse mobileRegistration(MobileRegistrationRequest mobileRegistrationRequest) {
        User user = new User();
        user.setName(mobileRegistrationRequest.getName());
        user.setPassword(passwordEncoder.encode(mobileRegistrationRequest.getPassword()));
        user.setEmail(mobileRegistrationRequest.getEmail());
        Role role = roleRepository.findByName(mobileRegistrationRequest.getRoleName());
        user.addRole(role);
        role.addUser(user);
        userRepository.save(user);
        return new AuthorizationResponse(jwtTokenUtil.generateToken(user), user.getEmail(), role.getRoleName());
    }

    @Override
    public AuthorizationResponse webRegistration(WebRegistrationRequest webRegistrationRequest) {
        User user = new User();
        user.setChildGardenName(webRegistrationRequest.getChildGardenName());
        user.setPassword(passwordEncoder.encode(webRegistrationRequest.getPassword()));
        user.setEmail(webRegistrationRequest.getEmail());
        user.setLanguage(webRegistrationRequest.getLanguage());
        user.setPhoneNumber(webRegistrationRequest.getPhoneNumber());
        Role role = roleRepository.findByName(webRegistrationRequest.getRoleName());
        user.addRole(role);
        role.addUser(user);
        userRepository.save(user);
        return new AuthorizationResponse(jwtTokenUtil.generateToken(user), user.getEmail(), role.getRoleName());

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("not found email"));
    }

    private boolean checkEmail(String email) {
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$";
        return patternMatches(email, regex);
    }

    public boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

}