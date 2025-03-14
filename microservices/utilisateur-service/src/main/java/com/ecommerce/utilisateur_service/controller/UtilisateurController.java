package com.ecommerce.utilisateur_service.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.utilisateur_service.model.Utilisateur;
import com.ecommerce.utilisateur_service.repository.UtilisateurRepository;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurController.class);

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // Lister tous les utilisateurs
    @GetMapping
    public List<Utilisateur> listerUtilisateurs() {
        logger.info("Listing all users");
        return utilisateurRepository.findAll();
    }

    // Créer un utilisateur
    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        logger.info("Creating a new user with username: {}", utilisateur.getUsername());
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        logger.info("User created successfully with ID: {}", savedUtilisateur.getId());
        return ResponseEntity.ok(savedUtilisateur);
    }

    // Modifier un utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> modifierUtilisateur(@PathVariable Long id, @RequestBody Utilisateur utilisateurModifie) {
        logger.info("Updating user with ID: {}", id);
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findById(id);
        if (utilisateurOpt.isEmpty()) {
            logger.warn("User not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }

        Utilisateur utilisateur = utilisateurOpt.get();
        utilisateur.setNom(utilisateurModifie.getNom());
        utilisateur.setUsername(utilisateurModifie.getUsername());
        utilisateur.setPassword(utilisateurModifie.getPassword());
        utilisateur.setAdresse(utilisateurModifie.getAdresse());

        Utilisateur updatedUtilisateur = utilisateurRepository.save(utilisateur);
        logger.info("User updated successfully with ID: {}", id);
        return ResponseEntity.ok(updatedUtilisateur);
    }

    // Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerUtilisateur(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findById(id);
        if (utilisateurOpt.isEmpty()) {
            logger.warn("User not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }

        utilisateurRepository.delete(utilisateurOpt.get());
        logger.info("User deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    // Connexion d'un utilisateur
    @PostMapping("/connexion")
    public ResponseEntity<Utilisateur> connexionUtilisateur(@RequestParam String username, @RequestParam String password) {
        logger.info("Attempting login for username: {}", username);
        // Rechercher l'utilisateur par username
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByUsername(username);

        // Vérifier si l'utilisateur existe et si le mot de passe correspond
        if (utilisateurOpt.isPresent() && utilisateurOpt.get().getPassword().equals(password)) {
            logger.info("Login successful for username: {}", username);
            return ResponseEntity.ok(utilisateurOpt.get()); // Connexion réussie
        } else {
            logger.warn("Login failed for username: {}", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Échec de la connexion
        }
    }
}