package br.com.fiap.oasis.oasis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Libera o acesso à página de login e arquivos estáticos (CSS/JS)
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                // O resto continua bloqueado
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")  // <--- AQUI ESTÁ A MÁGICA
                .defaultSuccessUrl("/app/rituais", true) // Para onde vai depois de logar?
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
            );
        
        return http.build();
    }
}