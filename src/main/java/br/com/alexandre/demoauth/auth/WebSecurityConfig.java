package br.com.alexandre.demoauth.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public WebSecurityConfig(DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint) {
        this.delegatedAuthenticationEntryPoint = delegatedAuthenticationEntryPoint;
    }

    public DockAuthenticationFilter dockAuthenticationFilter() throws Exception {
        return new DockAuthenticationFilter(authenticationManagerBean());
    }

    public DockAuthenticationProvider dockAuthenticationProvider() {
        return new DockAuthenticationProvider();
    }

    private final DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic().authenticationEntryPoint(this.delegatedAuthenticationEntryPoint)
                .and()
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt().and()
                        .authenticationEntryPoint(this.delegatedAuthenticationEntryPoint))
                .addFilterAt(dockAuthenticationFilter(), BasicAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(dockAuthenticationProvider());
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
