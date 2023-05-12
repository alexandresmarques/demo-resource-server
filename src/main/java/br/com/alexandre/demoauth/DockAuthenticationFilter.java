package br.com.alexandre.demoauth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DockAuthenticationFilter implements Filter {

    private AuthenticationManager authenticationManager;

    public DockAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String authorization = httpServletRequest.getHeader("access_token");
        if (authorization == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        try {
            DockAuthenticationToken dockAuthentication = new DockAuthenticationToken(authorization, null);
            Authentication authResult = authenticationManager.authenticate(dockAuthentication);
            if (authResult.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authResult);
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (AuthenticationException authenticationException) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}
