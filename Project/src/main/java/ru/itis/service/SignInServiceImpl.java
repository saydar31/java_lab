package ru.itis.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.dto.SignInDto;
import ru.itis.dto.UserDto;
import ru.itis.model.User;
import ru.itis.repositories.UserRepository;

import java.util.Optional;

@Component
public class SignInServiceImpl implements SignInService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public SignInServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto verificationResult(String email, String password) throws IllegalAccessException {
        Optional<User> userCandidate = userRepository.findByEmail(email);
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            if (passwordEncoder.matches(password, user.getPassWordHash())) {
                return UserDto.from(user);
            } else {
                throw new IllegalAccessException("wrong password");
            }
        } else {
            throw new IllegalAccessException("email dose not exist");
        }

    }

    @Override
    public UserDto verificationResult(SignInDto signInDto) throws IllegalAccessException {
        return verificationResult(signInDto.getEmail(), signInDto.getPassword());
    }
}
