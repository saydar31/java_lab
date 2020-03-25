package ru.itis.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import ru.itis.dto.UserDto;
import ru.itis.model.User;
import ru.itis.repositories.UserRepository;

import java.util.Optional;

@Component
public class ConfirmationServiceImpl implements ConfirmationService {

    private UserRepository userRepository;

    private Algorithm algorithm;
    private AutoLoginService autoLoginService;

    public ConfirmationServiceImpl(UserRepository userRepository, Algorithm algorithm, AutoLoginService autoLoginService) {
        this.userRepository = userRepository;
        this.algorithm = algorithm;
        this.autoLoginService = autoLoginService;
    }

    @Override
    public String getVerificationString(User user) {
        return JWT.create()
                .withIssuer("aydar")
                .withClaim("id", user.getId())
                .withClaim("email", user.getEmail())
                .sign(algorithm);
    }

    @Override
    public void verify(String hash) {
        DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(hash);
        Long id = decodedJWT.getClaim("id").asLong();
        Optional<User> userCandidate = userRepository.find(id);
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            userRepository.makeProofed(user);
            autoLoginService.autoLogin(user);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
