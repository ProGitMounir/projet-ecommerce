package com.ecommerce.utilisateur.service_utilisateur.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.utilisateur.service_utilisateur.model.Utilisateur;
import com.ecommerce.utilisateur.service_utilisateur.repository.UtilisateurRepository;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Utilisateur inscrireUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur modifierUtilisateur(Long id, Utilisateur utilisateur) {
        Optional<Utilisateur> existingUtilisateurOptional = utilisateurRepository.findById(id);
        if (existingUtilisateurOptional.isPresent()) {
            Utilisateur existingUtilisateur = existingUtilisateurOptional.get();
    
            if (utilisateur.getNom() != null) {
                existingUtilisateur.setNom(utilisateur.getNom());
            }
            if (utilisateur.getEmail() != null) {
                existingUtilisateur.setEmail(utilisateur.getEmail());
            }
            if (utilisateur.getMotDePasse() != null) {
                existingUtilisateur.setMotDePasse(utilisateur.getMotDePasse());
            }
            if (utilisateur.getAdresse() != null) {
                existingUtilisateur.setAdresse(utilisateur.getAdresse());
            }
            if (utilisateur.getMoyenPaiement() != null) {
                existingUtilisateur.setMoyenPaiement(utilisateur.getMoyenPaiement());
            }
    
            return utilisateurRepository.save(existingUtilisateur);
        } else {
            return null; 
        }
    }

    public List<Utilisateur> listerUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id).orElse(null);
    }
    
}
