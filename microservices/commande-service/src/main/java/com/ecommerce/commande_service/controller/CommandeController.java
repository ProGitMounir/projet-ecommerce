package com.ecommerce.commande_service.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.commande_service.dto.ProduitDTO;
import com.ecommerce.commande_service.model.Commande;
import com.ecommerce.commande_service.repository.CommandeRepository;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    private static final Logger logger = LoggerFactory.getLogger(CommandeController.class);

    @Autowired
    private RestTemplate restTemplate; // Utilise le RestTemplate configuré

    @Autowired
    private CommandeRepository commandeRepository;

    // Lister toutes les commandes
    @GetMapping
    public List<Commande> listerCommandes() {
        logger.info("Listing all commands");
        return commandeRepository.findAll();
    }

    // Créer une commande pour un utilisateur
    @PostMapping
    public ResponseEntity<Commande> createCommande(@RequestBody Commande commande) {
        logger.info("Creating a new command for product ID: {}", commande.getProduitId());

        // Vérifier si le produit est disponible
        ResponseEntity<ProduitDTO> produitResponse = restTemplate.exchange(
                "http://PRODUIT-SERVICE/api/produits/" + commande.getProduitId(), HttpMethod.GET, null, ProduitDTO.class);

        if (!produitResponse.getStatusCode().is2xxSuccessful()) {
            logger.warn("Product not found with ID: {}", commande.getProduitId());
            return ResponseEntity.notFound().build();
        }

        ProduitDTO produit = produitResponse.getBody();
        if (produit != null && produit.getStock() >= commande.getQuantite()) {
            // Calculer le prix total
            commande.setPrixTotal(produit.getPrixUnitaire() * commande.getQuantite());

            // Définir le statut initial de la commande
            commande.setStatut(Commande.STATUT_EN_ATTENTE);

            // Sauvegarder la commande
            logger.info("Command created successfully with ID: {}", commande.getId());
            return ResponseEntity.ok(commandeRepository.save(commande));
        } else {
            logger.warn("Insufficient stock for product ID: {}", commande.getProduitId());
            return ResponseEntity.badRequest().build();
        }
    }

    // Valider une commande (décrémenter le stock uniquement si la commande est validée)
    @PutMapping("/{id}/valider")
    public ResponseEntity<Commande> validerCommande(@PathVariable Long id) {
        logger.info("Validating command with ID: {}", id);

        Optional<Commande> commandeOpt = commandeRepository.findById(id);
        if (commandeOpt.isEmpty()) {
            logger.warn("Command not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }

        Commande commande = commandeOpt.get();

        // Vérifier si la commande est déjà validée
        if (Commande.STATUT_VALIDE.equals(commande.getStatut())) {
            logger.warn("Command with ID: {} is already validated", id);
            return ResponseEntity.badRequest().body(commande); // La commande est déjà validée
        }

        // Vérifier si le produit est toujours disponible
        ResponseEntity<ProduitDTO> produitResponse = restTemplate.exchange(
                "http://PRODUIT-SERVICE/api/produits/" + commande.getProduitId(), HttpMethod.GET, null, ProduitDTO.class);

        if (!produitResponse.getStatusCode().is2xxSuccessful()) {
            logger.warn("Product not found with ID: {}", commande.getProduitId());
            return ResponseEntity.notFound().build();
        }

        ProduitDTO produit = produitResponse.getBody();
        if (produit != null && produit.getStock() >= commande.getQuantite()) {
            // Décrémenter le stock du produit
            restTemplate.exchange("http://PRODUIT-SERVICE/api/produits/" + commande.getProduitId() + "/decrementer?quantity=" + commande.getQuantite(),
                    HttpMethod.PUT, null, Void.class);

            // Mettre à jour le statut de la commande
            commande.setStatut(Commande.STATUT_VALIDE);
            logger.info("Command with ID: {} validated successfully", id);
            return ResponseEntity.ok(commandeRepository.save(commande));
        } else {
            logger.warn("Insufficient stock for product ID: {}", commande.getProduitId());
            return ResponseEntity.badRequest().build(); // Stock insuffisant
        }
    }

    // Modifier une commande (uniquement si le statut est EN_ATTENTE)
    @PutMapping("/{id}")
    public ResponseEntity<Commande> modifierCommande(@PathVariable Long id, @RequestBody Commande commandeModifiee) {
        logger.info("Modifying command with ID: {}", id);

        Optional<Commande> commandeOpt = commandeRepository.findById(id);
        if (commandeOpt.isEmpty()) {
            logger.warn("Command not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }

        Commande commande = commandeOpt.get();

        // Vérifier si la commande est déjà validée
        if (Commande.STATUT_VALIDE.equals(commande.getStatut())) {
            logger.warn("Command with ID: {} is already validated and cannot be modified", id);
            return ResponseEntity.badRequest().body(commande); // La commande est validée et ne peut pas être modifiée
        }

        // Mettre à jour les champs modifiables
        commande.setProduitId(commandeModifiee.getProduitId());
        commande.setQuantite(commandeModifiee.getQuantite());

        // Recalculer le prix total
        ResponseEntity<ProduitDTO> produitResponse = restTemplate.exchange(
                "http://PRODUIT-SERVICE/api/produits/" + commande.getProduitId(), HttpMethod.GET, null, ProduitDTO.class);

        if (!produitResponse.getStatusCode().is2xxSuccessful()) {
            logger.warn("Product not found with ID: {}", commande.getProduitId());
            return ResponseEntity.notFound().build();
        }

        ProduitDTO produit = produitResponse.getBody();
        if (produit != null && produit.getStock() >= commande.getQuantite()) {
            commande.setPrixTotal(produit.getPrixUnitaire() * commande.getQuantite());
            logger.info("Command with ID: {} modified successfully", id);
            return ResponseEntity.ok(commandeRepository.save(commande));
        } else {
            logger.warn("Insufficient stock for product ID: {}", commande.getProduitId());
            return ResponseEntity.badRequest().build(); // Stock insuffisant
        }
    }

    // Supprimer une commande (uniquement si le statut est EN_ATTENTE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCommande(@PathVariable Long id) {
        logger.info("Deleting command with ID: {}", id);

        Optional<Commande> commandeOpt = commandeRepository.findById(id);
        if (commandeOpt.isEmpty()) {
            logger.warn("Command not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }

        Commande commande = commandeOpt.get();

        // Vérifier si la commande est déjà validée
        if (Commande.STATUT_VALIDE.equals(commande.getStatut())) {
            logger.warn("Command with ID: {} is already validated and cannot be deleted", id);
            return ResponseEntity.badRequest().build(); // La commande est validée et ne peut pas être supprimée
        }

        // Supprimer la commande
        commandeRepository.delete(commande);
        logger.info("Command with ID: {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }

    // Lister les commandes d'un utilisateur
    @GetMapping("/utilisateur/{utilisateurId}")
    public List<Commande> listerCommandesParUtilisateur(@PathVariable Long utilisateurId) {
        logger.info("Listing commands for user ID: {}", utilisateurId);
        return commandeRepository.findByUtilisateurId(utilisateurId);
    }
}