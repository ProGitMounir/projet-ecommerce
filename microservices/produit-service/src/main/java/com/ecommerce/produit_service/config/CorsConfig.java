package com.ecommerce.produit_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Autoriser les endpoints sous /api
                .allowedOrigins("http://localhost:4200") // Autoriser Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Méthodes autorisées
                .allowedHeaders("*") // Autoriser tous les en-têtes
                .allowCredentials(true); // Autoriser les cookies et les en-têtes d'authentification
    }
}