package br.com.alexandre.demoauth.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class DockAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public DockAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public DockAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}
