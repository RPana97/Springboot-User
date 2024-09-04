package org.example.usermanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    /**
     * Configure in-memory authentication with two users: admin and user.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withUsername("admin")
                        .password("{noop}admin123") // {noop} means no password encoder
                        .roles("ADMIN")
                        .build()
        );
        manager.createUser(
                User.withUsername("user")
                        .password("{noop}user123")
                        .roles("USER")
                        .build()
        );
        return manager;
    }

    /**
     * Configure HTTP security, defining which endpoints are secured and how.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for Postman testing
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/h2-console/**").permitAll() // Allow access without authentication
                        .anyRequest().authenticated() // Secure other endpoints
                )
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())) // Updated way for sameOrigin
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session
                .httpBasic(withDefaults()); // HTTP Basic Auth for protected routes

        return http.build();
    }

}

