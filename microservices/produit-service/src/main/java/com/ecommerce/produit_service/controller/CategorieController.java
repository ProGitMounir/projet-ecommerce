package com.ecommerce.produit_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.produit_service.model.Categorie;
import com.ecommerce.produit_service.repository.CategorieRepository;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {
    
    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping()
    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }
}
