package com.ecommerce.commande_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.commande_service.model.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByUtilisateurId(Long utilisateurId);
}
