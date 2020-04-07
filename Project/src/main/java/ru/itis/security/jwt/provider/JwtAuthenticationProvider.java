package ru.itis.security.jwt.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.itis.model.Role;
import ru.itis.model.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.security.jwt.authentication.JwtAuthenticationImpl;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private Algorithm algorithm;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();
        if (token != null) {
            DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
            User user = User.builder()
                    .id(decodedJWT.getClaim("id").asLong())
                    .role(Role.valueOf(decodedJWT.getClaim("role").asString()))
                    .email(decodedJWT.getClaim("emal").asString())
                    .proofed(decodedJWT.getClaim("isProofed").asBoolean())
                    .build();
            UserDetails userDetails = new UserDetailsImpl(user);
            ((JwtAuthenticationImpl) authentication).setUserDetails(userDetails);
            authentication.setAuthenticated(true);
        } else {
            authentication.setAuthenticated(false);
        }
        return authentication;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return JwtAuthenticationImpl.class.equals(aClass);
    }
}
