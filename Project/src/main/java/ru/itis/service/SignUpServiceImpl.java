package ru.itis.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.model.Role;
import ru.itis.model.User;
import ru.itis.repositories.UserRepository;

@Component
public class SignUpServiceImpl implements SignUpService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private MailService mailService;

    private ConfirmationService confirmationService;

    private final AutoLoginService autoLoginService;

    public SignUpServiceImpl(@Qualifier("userRepositoryJdbcTemplateImpl") UserRepository userRepository, PasswordEncoder passwordEncoder, MailService mailService, ConfirmationService confirmationService, AutoLoginService autoLoginService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.confirmationService = confirmationService;
        this.autoLoginService = autoLoginService;
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
        //autoLoginService.autoLogin(user);
    }
}
