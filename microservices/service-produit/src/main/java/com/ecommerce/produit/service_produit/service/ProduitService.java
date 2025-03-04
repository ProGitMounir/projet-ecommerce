package com.ecommerce.produit.service_produit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.produit.service_produit.model.Produit;
import com.ecommerce.produit.service_produit.repository.ProduitRepository;


@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    public Produit ajouterProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    public Produit modifierProduit(Long id, Produit produit) {
        Optional<Produit> existingProductOptional = produitRepository.findById(id);
        if (existingProductOptional.isPresent()) {
            Produit existingProduct = existingProductOptional.get();
    
            // Mettre à jour uniquement les champs non nuls
            if (produit.getNom() != null) {
                existingProduct.setNom(produit.getNom());
            }
            if (produit.getCategorie() != null) {
                existingProduct.setCategorie(produit.getCategorie());
            }
            if (produit.getPrix() != null) {
                existingProduct.setPrix(produit.getPrix());
            }
            if (produit.getStock() != null) {
                existingProduct.setStock(produit.getStock());
            }
    
            return produitRepository.save(existingProduct);
        }
        return null; // Retourne null si le produit n'existe pas
    }

    public void supprimerProduit(Long id) {
        produitRepository.deleteById(id);
    }

    public List<Produit> listerProduits() {
        return produitRepository.findAll();
    }
    public List<Produit> listerProduitsDisponibles() {
        return produitRepository.findByDisponibleTrue();
    }
    

    public Produit getProduitById(Long id) {
        return produitRepository.findById(id).orElse(null);
    }

    public void mettreAJourStock(Long id, Integer quantite) {
        Produit produit = produitRepository.findById(id).orElse(null);
        if (produit != null) {
            produit.setStock(produit.getStock() + quantite);
            produit.setDisponible(produit.getStock() > 0); // Met à jour la disponibilité
            produitRepository.save(produit);
        }
    }
    
}
