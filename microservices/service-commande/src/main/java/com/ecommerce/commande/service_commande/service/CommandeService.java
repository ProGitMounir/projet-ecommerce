package com.ecommerce.commande.service_commande.service;

import java.util.Date;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecommerce.commande.service_commande.config.RabbitMQConfig;
import com.ecommerce.commande.service_commande.model.Commande;
import com.ecommerce.commande.service_commande.repository.CommandeRepository;

import main.java.com.projetecommerce.common.messaging.StockUpdateMessage;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;


    /*public Commande creerCommande(Commande commande) {
        commande.setDateCreation(new java.sql.Date(new Date().getTime()));
        commande.setStatut("EN_ATTENTE");
        return commandeRepository.save(commande);
    }*/
    public Commande creerCommande(Commande commande) {
        commande.setDateCreation(new java.sql.Date(new Date().getTime()));
        commande.setStatut("EN_ATTENTE");

        Commande savedCommande = commandeRepository.save(commande);

        // Envoyer un message pour mettre à jour le stock
        StockUpdateMessage message = new StockUpdateMessage(commande.getProduitId(), commande.getQuantite());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);

        return savedCommande;
    }

    public Commande validerCommande(Long id) {
        Commande commande = commandeRepository.findById(id).orElse(null);
        if (commande != null) {
            commande.setStatut("EN_COURS");
            return commandeRepository.save(commande);
        }
        return null;
    }
    /*public Commande validerCommande(Long id) {
        Commande commande = commandeRepository.findById(id).orElse(null);
        if (commande != null) {
            commande.setStatut("EN_COURS");
            commandeRepository.save(commande);

            // Envoi du message pour mettre à jour le stock du produit
            StockUpdateMessage message = new StockUpdateMessage(commande.getProduitId(), commande.getQuantite());
            rabbitTemplate.convertAndSend(exchange, routingKey, message);

            return commande;
        }
        return null;
    }*/

    public List<Commande> listerCommandes() {
        return commandeRepository.findAll();
    }

    public Commande getCommandeById(Long id) {
        return commandeRepository.findById(id).orElse(null);
    }
}