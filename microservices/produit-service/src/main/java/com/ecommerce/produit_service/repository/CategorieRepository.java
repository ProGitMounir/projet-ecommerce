package com.ecommerce.produit_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.produit_service.model.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    
}