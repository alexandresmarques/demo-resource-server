package br.com.alexandre.demoauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class DemoAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoAuthApplication.class, args);
    }

    @Bean
    @Primary
    OAuth2ResourceServerProperties oAuth2ResourceServerProperties() {
        return new OAuth2ResourceServerProperties();
    }
}
