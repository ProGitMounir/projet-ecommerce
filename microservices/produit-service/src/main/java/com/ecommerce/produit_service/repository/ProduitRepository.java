package com.ecommerce.produit_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.produit_service.model.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    // Filtrer les produits par prix (inférieur ou égal à un prix donné)
    @Query("SELECT p FROM Produit p WHERE p.prixUnitaire <= :prix")
    List<Produit> findByPrixUnitaireLessThanOrEqual(@Param("prix") double prix);

    // Filtrer les produits disponibles (stock > 0)
    @Query("SELECT p FROM Produit p WHERE p.stock > 0")
    List<Produit> findAvailableProducts();

    // Recherche par catégorie
    List<Produit> findByCategorie_Id(Long categorieId);
}

