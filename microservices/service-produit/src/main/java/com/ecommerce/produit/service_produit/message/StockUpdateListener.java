package com.ecommerce.produit.service_produit.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.produit.service_produit.service.ProduitService;

import main.java.com.projetecommerce.common.messaging.StockUpdateMessage;

@Service
public class StockUpdateListener {

    @Autowired
    private ProduitService produitService;

    @RabbitListener(queues = "stock-update-queue")  // La queue où les messages sont envoyés
    public void receiveMessage(StockUpdateMessage message) {
        System.out.println("Message reçu : " + message);

        // Logique pour mettre à jour le stock du produit
        // Par exemple, rechercher le produit par son ID et mettre à jour le stock
        Long produitId = message.getProduitId();
        int quantite = message.getQuantite();

        // Ici, tu appelles ton service Produit pour mettre à jour le stock
        produitService.mettreAJourStock(produitId, quantite);
    }
}
