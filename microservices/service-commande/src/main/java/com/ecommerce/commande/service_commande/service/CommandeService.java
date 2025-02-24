package com.ecommerce.commande.service_commande.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.commande.service_commande.model.Commande;
import com.ecommerce.commande.service_commande.repository.CommandeRepository;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    public Commande creerCommande(Commande commande) {
        commande.setDateCreation(new java.sql.Date(new Date().getTime()));
        commande.setStatut("EN_ATTENTE");
        return commandeRepository.save(commande);
    }

    public Commande validerCommande(Long id) {
        Commande commande = commandeRepository.findById(id).orElse(null);
        if (commande != null) {
            commande.setStatut("EN_COURS");
            return commandeRepository.save(commande);
        }
        return null;
    }

    public List<Commande> listerCommandes() {
        return commandeRepository.findAll();
    }

    public Commande getCommandeById(Long id) {
        return commandeRepository.findById(id).orElse(null);
    }
}