package ru.itis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.itis.model.User;
import ru.itis.security.details.UserDetailsImpl;

@Component
public class AutoLoginServiceImpl implements AutoLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    public AutoLoginServiceImpl() {
    }


    @Override
    public void autoLogin(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        if (authenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }
}
