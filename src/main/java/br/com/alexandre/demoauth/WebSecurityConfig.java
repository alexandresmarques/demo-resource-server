package br.com.alexandre.demoauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtBearerTokenAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    public WebSecurityConfig(OAuth2ResourceServerProperties oAuth2ResourceServerProperties) {
        this.oAuth2ResourceServerProperties = oAuth2ResourceServerProperties;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .authenticationManagerResolver(customAuthenticationManager());
        return http.build();
    }

    private AuthenticationManagerResolver<HttpServletRequest> customAuthenticationManager() {
//        LinkedHashMap<RequestMatcher, AuthenticationManager> authenticationManagers = new LinkedHashMap<>();
//
//        // USE JWT tokens (locally validated) to validate HEAD, GET, and OPTIONS requests
//        List<String> readMethod = Arrays.asList("HEAD", "GET", "OPTIONS");
//        RequestMatcher readMethodRequestMatcher = request -> readMethod.contains(request.getMethod());
//        authenticationManagers.put(readMethodRequestMatcher, jwt());

        // JWT
        CustomAuthenticationManagerResolver authenticationManagerResolver
                = new CustomAuthenticationManagerResolver(jwt());

        // access_token
        authenticationManagerResolver.setDefaultAuthenticationManager(jwt());
        return authenticationManagerResolver;
    }

    private AuthenticationManager jwt() {
        String jwkSetUri = oAuth2ResourceServerProperties.getJwt().getIssuerUri();

        JwtAuthenticationProvider authenticationProvider = new JwtAuthenticationProvider(JwtDecoders.fromIssuerLocation(jwkSetUri));
        return authenticationProvider::authenticate;

    }

    // Mimic the default configuration for opaque token validation
//    private AuthenticationManager opaque() {
//        String issuer = oAuth2ClientProperties.getProvider().get("okta").getIssuerUri();
//        String introspectionUri = issuer + "/v1/introspect";
//
//        // The default opaque token logic
//        OAuth2ClientProperties.Registration oktaRegistration = oAuth2ClientProperties.getRegistration().get("okta");
//        OpaqueTokenIntrospector introspectionClient = new NimbusOpaqueTokenIntrospector(
//                introspectionUri,
//                oktaRegistration.getClientId(),
//                oktaRegistration.getClientSecret());
//        return new OpaqueTokenAuthenticationProvider(introspectionClient)::authenticate;
//    }
}
