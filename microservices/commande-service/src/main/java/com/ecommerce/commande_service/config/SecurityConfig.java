package com.ecommerce.commande_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .cors().and() // Active CORS
            .authorizeRequests()
            .requestMatchers("/api/public").permitAll()  // Autoriser l'accès public à /api/public
            .requestMatchers("/api/**").authenticated()  // Protéger tous les autres endpoints sous /api/**
            .anyRequest().permitAll()  // Autoriser l'accès à tous les autres endpoints sans authentification
            .and()
            .httpBasic();  // Utiliser l'authentification de base (Basic Auth)
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    // Configuration CORS
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Appliquer CORS aux endpoints sous /api
                    .allowedOrigins("http://localhost:4200") // Autoriser Angular
                    .allowedMethods("GET", "POST", "PUT", "DELETE") // Méthodes autorisées
                    .allowedHeaders("*") // Autoriser tous les en-têtes
                    .allowCredentials(true); // Autoriser les cookies et les en-têtes d'authentification
            }
        };
    }
}
