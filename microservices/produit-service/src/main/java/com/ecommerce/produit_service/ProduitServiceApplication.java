package com.ecommerce.produit_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.produit_service.model.Categorie;
import com.ecommerce.produit_service.repository.CategorieRepository;

@SpringBootApplication
@EnableDiscoveryClient 
public class ProduitServiceApplication implements CommandLineRunner{

	@Autowired
    private CategorieRepository categorieRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProduitServiceApplication.class, args);
	}
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

	@Override
    public void run(String... args) throws Exception {
        // Insertion de qlq catégorie
        Categorie electronique = new Categorie();
        electronique.setNom("Électronique");
        categorieRepository.save(electronique);

        Categorie vetements = new Categorie();
        vetements.setNom("Vêtements");
        categorieRepository.save(vetements);

        Categorie alimentation = new Categorie();
        alimentation.setNom("Alimentation");
        categorieRepository.save(alimentation);

        Categorie accessoires = new Categorie();
        accessoires.setNom("Accessoires");
        categorieRepository.save(accessoires);

        Categorie livres = new Categorie();
        livres.setNom("Livres");
        categorieRepository.save(livres);
	}

}
