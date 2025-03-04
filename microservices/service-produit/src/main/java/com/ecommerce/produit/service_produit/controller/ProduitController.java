package com.ecommerce.produit.service_produit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.produit.service_produit.model.Produit;
import com.ecommerce.produit.service_produit.service.ProduitService;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @PostMapping
    public Produit ajouterProduit(@RequestBody Produit produit) {
        return produitService.ajouterProduit(produit);
    }

    @PutMapping("/{id}")
    public Produit modifierProduit(@PathVariable Long id, @RequestBody Produit produit) {
        return produitService.modifierProduit(id, produit);
    }

    @DeleteMapping("/{id}")
    public void supprimerProduit(@PathVariable Long id) {
        produitService.supprimerProduit(id);
    }

    @GetMapping
    public List<Produit> listerProduits() {
        return produitService.listerProduits();
    }

    @GetMapping("/disponibles")
    public List<Produit> getProduitsDisponibles() {
        return produitService.listerProduitsDisponibles();
    }

    @GetMapping("/{id}")
    public Produit getProduitById(@PathVariable Long id) {
        return produitService.getProduitById(id);
    }

    @PostMapping("/{id}/stock")
    public void mettreAJourStock(@PathVariable Long id, @RequestParam Integer quantite) {
        produitService.mettreAJourStock(id, quantite);
    }
}
