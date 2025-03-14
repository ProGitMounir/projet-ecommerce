package com.ecommerce.utilisateur_service;

import java.util.Base64;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient 
public class UtilisateurServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtilisateurServiceApplication.class, args);
	}
	@Bean
	@LoadBalanced // Active la résolution de service via Eureka
	/*public RestTemplate restTemplate() {
		return new RestTemplate();
	}*/
	public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Ajouter un interceptor pour l'authentification de base
        restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
            String credentials = "user:password"; // Remplacez par les mêmes credentials que dans SecurityConfig
            String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());
            request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials);
            return execution.execute(request, body);
        }));

        return restTemplate;
    }
}
