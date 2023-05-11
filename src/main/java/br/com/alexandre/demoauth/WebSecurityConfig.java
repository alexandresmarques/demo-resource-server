package br.com.alexandre.demoauth;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    private final AuthenticationFilter authenticationFilter;

    private final AccessTokenAuthenticationProvider accessTokenAuthenticationProvider;

    public WebSecurityConfig(OAuth2ResourceServerProperties oAuth2ResourceServerProperties, AuthenticationFilter authenticationFilter, AccessTokenAuthenticationProvider accessTokenAuthenticationProvider) {
        this.oAuth2ResourceServerProperties = oAuth2ResourceServerProperties;
        this.authenticationFilter = authenticationFilter;
        this.accessTokenAuthenticationProvider = accessTokenAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .addFilter(authenticationFilter);

        return http.build();
    }
    

//    private AuthenticationManagerResolver<HttpServletRequest> customAuthenticationManager() {
////        LinkedHashMap<RequestMatcher, AuthenticationManager> authenticationManagers = new LinkedHashMap<>();
////
////        // USE JWT tokens (locally validated) to validate HEAD, GET, and OPTIONS requests
////        List<String> readMethod = Arrays.asList("HEAD", "GET", "OPTIONS");
////        RequestMatcher readMethodRequestMatcher = request -> readMethod.contains(request.getMethod());
////        authenticationManagers.put(readMethodRequestMatcher, jwt());
//
//        //Os paths que vierem com /api/v2 tem que está autenticado via oauth2 ou access_token
//
//        //Os paths que comecarem com /manager usar UsernamePasswordAuthenticationToken
//        //Os demais paths ignorar
//
//        // JWT
//        CustomAuthenticationManagerResolver authenticationManagerResolver
//                = new CustomAuthenticationManagerResolver(jwt());
//
//        // access_token
//        authenticationManagerResolver.setDefaultAuthenticationManager(jwt());
//        return authenticationManagerResolver;
//    }
//
//    private AuthenticationManager jwt() {
//        String jwkSetUri = oAuth2ResourceServerProperties.getJwt().getIssuerUri();
//
//        JwtAuthenticationProvider authenticationProvider = new JwtAuthenticationProvider(JwtDecoders.fromIssuerLocation(jwkSetUri));
//        return authenticationProvider::authenticate;
//
//    }

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
