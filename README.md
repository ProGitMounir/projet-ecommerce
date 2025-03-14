# Projet E-commerce

## Description

Ce projet est une application e-commerce basée sur une architecture microservices. Il comprend un frontend développé avec Angular et plusieurs services backend développés avec Spring Boot.

## Architecture

Le projet est composé des éléments suivants :

- **appFrontEnd** : Interface utilisateur développée avec Angular.
- **eureka-server** : Serveur de découverte Eureka pour la gestion des microservices.
- **commande-service** : Microservice de gestion des commandes.
- **produit-service** : Microservice de gestion des produits.
- **utilisateur-service** : Microservice de gestion des utilisateurs.

## Prérequis

Avant d'exécuter le projet, assurez-vous d'avoir installé :

- **Node.js** et **Angular CLI** pour le frontend
- **Java 17+** et **Maven** pour les microservices
- **MySQL** pour la base de données

## Installation et Exécution

### 1. Lancer le serveur Eureka
      cd eureka-server
      mvn spring-boot:run

### 2. Démarrer les microservices

Lancer chaque service dans un terminal différent :

      cd commande-service
      mvn spring-boot:run
      
      cd produit-service
      mvn spring-boot:run
      
      cd utilisateur-service
      mvn spring-boot:run

### 3. Démarrer le frontend Angular
      cd appFrontEnd
      npm install
      ng serve

## Auteur
Iya Amine Mounir
