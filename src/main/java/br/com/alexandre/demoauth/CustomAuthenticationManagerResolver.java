package br.com.alexandre.demoauth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomAuthenticationManagerResolver implements AuthenticationManagerResolver<HttpServletRequest> {

    private final AuthenticationManager authenticationManager;

    private AuthenticationManager defaultAuthenticationManager = authentication -> {
        throw new AuthenticationServiceException("Cannot authenticate " + authentication);
    };

    public CustomAuthenticationManagerResolver(AuthenticationManager authenticationManager) {
        Assert.notNull(authenticationManager, "authenticationManager cannot be null");
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationManager resolve(HttpServletRequest context) {

//        for (Map.Entry<RequestMatcher, AuthenticationManager> entry : this.authenticationManagers.entrySet()) {
//            if (authenticationManager..getKey().matches(context)) {
//                return entry.getValue();
//            }
//        }
        return this.defaultAuthenticationManager;
    }

    public void setDefaultAuthenticationManager(AuthenticationManager defaultAuthenticationManager) {
        Assert.notNull(defaultAuthenticationManager, "defaultAuthenticationManager cannot be null");
        this.defaultAuthenticationManager = defaultAuthenticationManager;
    }
}
