package ru.itis.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.dto.JwtAuthenticationDto;
import ru.itis.dto.SignInDto;
import ru.itis.dto.UserDto;
import ru.itis.model.User;
import ru.itis.repositories.UserRepository;

import java.util.Optional;

@Component
public class SignInServiceImpl implements SignInService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;


    private Algorithm algorithm;

    public SignInServiceImpl(@Qualifier("userRepositoryJdbcTemplateImpl") UserRepository userRepository, PasswordEncoder passwordEncoder, Algorithm algorithm) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.algorithm = algorithm;
    }

    @Override
    public JwtAuthenticationDto verificationResult(SignInDto signInDto) throws IllegalAccessException {
        Optional<User> userOptional = userRepository.findByEmail(signInDto.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(signInDto.getPassword(), user.getPassWordHash())) {
                String token = JWT.create()
                        .withClaim("id", user.getId())
                        .withClaim("email", user.getEmail())
                        .withClaim("role", user.getRole().name())
                        .withClaim("isProofed", user.isProofed())
                        .sign(algorithm);
                return new JwtAuthenticationDto(token);
            } else {
                throw new IllegalAccessException();
            }
        } else {
            throw new IllegalAccessException();
        }
    }
}
