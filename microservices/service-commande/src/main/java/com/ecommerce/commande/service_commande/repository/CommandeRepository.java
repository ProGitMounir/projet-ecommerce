package com.ecommerce.commande.service_commande.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.commande.service_commande.model.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
}
