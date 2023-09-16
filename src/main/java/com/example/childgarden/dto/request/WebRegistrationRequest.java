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
import validations.PhoneNumberValidation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebRegistrationRequest {

    private String childGardenName;

    private String language;

    @EmailValidation
    @NotBlank
    @NotNull
    private String email;

    @PasswordValidation
    private String password;

    private String roleName;


    @PhoneNumberValidation
    private String phoneNumber;

}
