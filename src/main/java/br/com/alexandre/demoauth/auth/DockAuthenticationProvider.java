package br.com.alexandre.demoauth.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class DockAuthenticationProvider implements AuthenticationProvider {

    public static final String ACCESS_TOKEN = "15banco";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var access_token = authentication.getName();
        if (access_token.equals(ACCESS_TOKEN)) {
            return new DockAuthenticationToken(null, null, null);
        }else{
            throw new BadCredentialsException("Access token value is not correct");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(DockAuthenticationToken.class);
    }
}
