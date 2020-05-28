package ru.itis.security.details;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.itis.model.User;
import ru.itis.repositories.UserRepository;

import java.util.Optional;

@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Qualifier("userRepositoryJpaImpl")
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> userCandidate = userRepository.findByEmail(s);
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            log.info("user: "+user.toString()+" got from db");
            return new UserDetailsImpl(user);
        } else {
            throw new UsernameNotFoundException("user with email " + s + " dose not exist");
        }
    }
}
