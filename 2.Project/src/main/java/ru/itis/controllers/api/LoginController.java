package ru.itis.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.JwtAuthenticationDto;
import ru.itis.dto.SignInDto;
import ru.itis.service.SignInService;


@RestController
public class LoginController {
    @Autowired
    private SignInService signInService;

    @PostMapping("/api/login")
    public ResponseEntity<JwtAuthenticationDto> post(@RequestBody SignInDto signInDto) {
        try {
            return ResponseEntity.ok(signInService.verificationResult(signInDto));
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException();
        }
    }
}
