package br.com.alexandre.demoauth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class DockAuthenticationToken extends AbstractAuthenticationToken {

    private Object credentials;

    public DockAuthenticationToken(Object credentials) {
        super((Collection) null);
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    public DockAuthenticationToken(Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.credentials = credentials;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
