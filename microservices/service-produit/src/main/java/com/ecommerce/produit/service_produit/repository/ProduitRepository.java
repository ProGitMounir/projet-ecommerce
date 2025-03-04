package com.ecommerce.produit.service_produit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.produit.service_produit.model.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    List<Produit> findByDisponibleTrue();
}