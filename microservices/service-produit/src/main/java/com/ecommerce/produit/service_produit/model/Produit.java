package com.ecommerce.produit.service_produit.model;

import jakarta.persistence.*;

@Entity
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String categorie;
    private Double prix;
    private Integer stock;

    // Getters
    public Long getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getCategorie() {
        return categorie;
    }
    public Double getPrix() {
        return prix;
    }
    public Integer getStock() {
        return stock;
    }

    // Setters
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }      
    public void setPrix(Double prix) {
        this.prix = prix;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    } 
    

}