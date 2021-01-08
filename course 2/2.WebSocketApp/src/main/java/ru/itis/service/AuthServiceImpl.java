package ru.itis.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.dto.SignInDto;
import ru.itis.model.Authority;
import ru.itis.model.User;
import ru.itis.repositories.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Component
public class AuthServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Algorithm algorithm;

    private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    @Override
    public boolean checkCookie(String cookie) {
        try {
            DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(cookie);
            Date date = decodedJWT.getClaim("deathDate").asDate();
            Date currentDate = new Date();
            return currentDate.compareTo(date) < 0;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    @Override
    public String signIn(SignInDto signInDto) {
        String userName = signInDto.getUserName();
        String password = signInDto.getPassword();
        Optional<User> userOptional = userRepository.findByName(userName);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPasswordHash().equals(password)) {
                return getToken(user);
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            User user = User.builder()
                    .userName(userName)
                    .passwordHash(password)
                    .authority(Authority.USER)
                    .build();
            userRepository.save(user);
            return getToken(user);
        }
    }

    @Override
    public User getUserByToken(String token) {
        DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
        Long userId = decodedJWT.getClaim("userId").asLong();
        Optional<User> userOptional = userRepository.find(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new IllegalArgumentException();
    }

    private String getToken(User user) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.plus(2, ChronoUnit.WEEKS);
        return JWT.create()
                .withClaim("userId", user.getId())
                .withClaim("userName", user.getUserName())
                .withClaim("authority", user.getAuthority().name())
                .withClaim("deathDate", convertToDateViaInstant(localDateTime))
                .sign(algorithm);
    }

}
