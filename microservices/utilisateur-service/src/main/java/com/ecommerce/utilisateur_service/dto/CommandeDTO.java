package com.ecommerce.utilisateur_service.dto;

public class CommandeDTO {
    private Long produitId;
    private int quantite;

    // Getters et Setters
    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}