package com.ecommerce.produit_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Ceci est un endpoint public de produit!";
    }

    @GetMapping("/private")
    public String privateEndpoint() {
        return "Ceci est un endpoint privé de produit!";
    }
}
