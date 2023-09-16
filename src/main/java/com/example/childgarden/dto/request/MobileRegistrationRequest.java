package com.example.childgarden.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import validations.EmailValidation;
import validations.PasswordValidation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MobileRegistrationRequest {

    private String name;

    private String surname;

    @EmailValidation
    @NotBlank
    @NotNull
    private String email;

    @PasswordValidation
    private String password;

    private String roleName;

}
