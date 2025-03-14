package com.ecommerce.produit_service.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.produit_service.model.Categorie;
import com.ecommerce.produit_service.model.Produit;
import com.ecommerce.produit_service.repository.ProduitRepository;
import com.ecommerce.produit_service.service.CategorieService;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    private static final Logger logger = LoggerFactory.getLogger(ProduitController.class);

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CategorieService categorieService;

    // Récupérer tous les produits
    @GetMapping
    public List<Produit> getAllProduits() {
        logger.info("Fetching all products");
        return produitRepository.findAll();
    }

    // Récupérer un produit par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        logger.info("Fetching product with ID: {}", id);
        Optional<Produit> produit = produitRepository.findById(id);
        if (produit.isPresent()) {
            logger.info("Product found with ID: {}", id);
            return ResponseEntity.ok(produit.get());
        } else {
            logger.warn("Product not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Ajouter un nouveau produit
    @PostMapping
    public Produit ajouterProduit(@RequestBody Produit produit) {
        logger.info("Adding a new product: {}", produit.getNom());

        // Vérifie si la catégorie existe
        Categorie categorie = categorieService.getCategorieById(produit.getCategorie().getId());
        if (categorie == null) {
            logger.error("Category not found with ID: {}", produit.getCategorie().getId());
            throw new RuntimeException("Catégorie non trouvée");
        }

        produit.setCategorie(categorie);  // Assigne la catégorie au produit
        Produit savedProduit = produitRepository.save(produit);
        logger.info("Product added successfully with ID: {}", savedProduit.getId());
        return savedProduit;
    }

    // Modifier un produit existant
    @PutMapping("/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable Long id, @RequestBody Produit produitDetails) {
        logger.info("Updating product with ID: {}", id);
        Optional<Produit> produitOpt = produitRepository.findById(id);
        if (produitOpt.isEmpty()) {
            logger.warn("Product not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }

        Produit produit = produitOpt.get();
        produit.setNom(produitDetails.getNom());
        produit.setStock(produitDetails.getStock());
        produit.setPrixUnitaire(produitDetails.getPrixUnitaire());

        Produit updatedProduit = produitRepository.save(produit);
        logger.info("Product updated successfully with ID: {}", id);
        return ResponseEntity.ok(updatedProduit);
    }

    // Décrémenter le stock d'un produit
    @PutMapping("/{id}/decrementer")
    public ResponseEntity<Void> decrementerStock(@PathVariable Long id, @RequestParam int quantity) {
        logger.info("Decrementing stock for product ID: {} by quantity: {}", id, quantity);
        Optional<Produit> produit = produitRepository.findById(id);
        if (produit.isPresent() && produit.get().getStock() >= quantity) {
            produit.get().setStock(produit.get().getStock() - quantity);
            produitRepository.save(produit.get());
            logger.info("Stock decremented successfully for product ID: {}", id);
            return ResponseEntity.ok().build();
        } else {
            logger.warn("Failed to decrement stock for product ID: {}", id);
            return ResponseEntity.badRequest().build();
        }
    }

    // Supprimer un produit
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        logger.info("Deleting product with ID: {}", id);
        Optional<Produit> produitOpt = produitRepository.findById(id);
        if (produitOpt.isEmpty()) {
            logger.warn("Product not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }

        produitRepository.delete(produitOpt.get());
        logger.info("Product deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    // Filtrer les produits par prix (inférieur ou égal à un prix donné)
    @GetMapping("/filtrer/prix")
    public List<Produit> filtrerProduitsParPrix(@RequestParam double prix) {
        logger.info("Filtering products by price less than or equal to: {}", prix);
        return produitRepository.findByPrixUnitaireLessThanOrEqual(prix);
    }

    // Filtrer les produits disponibles (stock > 0)
    @GetMapping("/filtrer/disponibilite")
    public List<Produit> filtrerProduitsDisponibles() {
        logger.info("Fetching available products (stock > 0)");
        return produitRepository.findAvailableProducts();
    }

    // Filtrer par catégorie
    @GetMapping("/filtrer/categorie/{categorieId}")
    public List<Produit> getProduitsByCategorie(@PathVariable Long categorieId) {
        logger.info("Fetching products by category ID: {}", categorieId);
        return produitRepository.findByCategorie_Id(categorieId);
    }
}