package com.example.childgarden.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MobileRegistrationRequest {

    private String name;

    private String surname;

    @Email
    @NotBlank
    @NotNull
    private String email;

    private String password;

    private String roleName;

}
