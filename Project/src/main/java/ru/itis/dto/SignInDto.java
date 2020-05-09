package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignInDto {
    @Email(message = "error.wrong.email")
    private String email;
    @NotEmpty(message = "error.wrong.password")
    private String password;
}
