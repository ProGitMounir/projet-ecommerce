package com.ecommerce.produit_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.produit_service.model.Categorie;
import com.ecommerce.produit_service.repository.CategorieRepository;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }
    public Categorie getCategorieById(Long id) {
        return categorieRepository.findById(id).orElse(null); // Retourne null si la catégorie n'est pas trouvée
    }
}
