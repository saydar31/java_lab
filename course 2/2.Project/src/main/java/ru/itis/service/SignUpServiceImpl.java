package ru.itis.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.dto.SignUpDto;
import ru.itis.model.Role;
import ru.itis.model.User;
import ru.itis.repositories.UserRepository;

@Component
public class SignUpServiceImpl implements SignUpService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private MailService mailService;

    private ConfirmationService confirmationService;

    public SignUpServiceImpl(@Qualifier("userRepositoryJdbcTemplateImpl") UserRepository userRepository, PasswordEncoder passwordEncoder, MailService mailService, ConfirmationService confirmationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.confirmationService = confirmationService;
    }

    @Override
    public void signUp(String email, String password) throws IllegalArgumentException {
        String passwordHash = passwordEncoder.encode(password);
        User user = User.builder()
                .email(email)
                .passWordHash(passwordHash)
                .role(Role.USER)
                .build();
        userRepository.save(user);
        mailService.sendSignUpLetter(user, confirmationService.getVerificationString(user));
    }

    @Override
    public void signUp(SignUpDto signUpDto) {
        signUp(signUpDto.getEmail(),signUpDto.getPassword());
    }
}
