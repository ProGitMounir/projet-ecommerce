package com.ecommerce.commande_service.dto;

public class ProduitDTO {
    private Long id;
    private String nom;
    private int stock;
    private double prixUnitaire;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public double getPrixUnitaire() {
        return prixUnitaire;
    }
    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
}
