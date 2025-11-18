package br.com.fiap.oasis.oasis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // API REST (Stateless) 
            .authorizeHttpRequests(auth -> auth
                // Permite acesso livre à documentação (Swagger)
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults()); 
        return http.build();
    }
}