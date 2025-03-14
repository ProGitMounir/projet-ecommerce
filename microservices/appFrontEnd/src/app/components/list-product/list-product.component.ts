import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DhCurrencyPipe } from '../../dh-currency.pipe';

@Component({
  selector: 'app-list-product',
  imports: [ReactiveFormsModule, CommonModule, FormsModule, DhCurrencyPipe],
  templateUrl: './list-product.component.html',
  styleUrl: './list-product.component.css'
})
export class ListProductComponent implements OnInit {
  categorieList: any[] = [];
  produitList: any[] = [];
  selectedCategoryId: any = null;
  selectedMaxPrice: number | null = null;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.getCategorie();
    this.getProduit();
  }

  getCategorie() {
    this.http.get('http://localhost:8081/api/categories').subscribe({
      next: (result: any) => {
        console.log('Catégories récupérées:', result);
        this.categorieList = result;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des catégories:', err);
      },
    });
  }

  getProduit(categorieId: any = null) {
    let url = 'http://localhost:8081/api/produits';
    if (categorieId != null) {
      url += `/filtrer/categorie/${categorieId}`;
    }
    this.http.get(url).subscribe({
      next: (result: any) => {
        console.log('Produits récupérés:', result);
        this.produitList = result;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des produits:', err);
      },
    });
  }

  onCategoryChange() {
    console.log('Catégorie sélectionnée:', this.selectedCategoryId);
    if (this.selectedCategoryId) {
      this.getProduit(this.selectedCategoryId);
    } else {
      this.getProduit();
    }
  }

  filterByAvailability() {
    this.http.get('http://localhost:8081/api/produits/filtrer/disponibilite').subscribe({
      next: (result: any) => {
        console.log('Produits disponibles:', result);
        this.produitList = result;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des produits disponibles:', err);
      },
    });
  }

  filterByPrice() {
    if (this.selectedMaxPrice != null) {
      this.http.get(`http://localhost:8081/api/produits/filtrer/prix?prix=${this.selectedMaxPrice}`).subscribe({
        next: (result: any) => {
          console.log('Produits filtrés par prix:', result);
          this.produitList = result;
        },
        error: (err) => {
          console.error('Erreur lors de la récupération des produits filtrés par prix:', err);
        },
      });
    }
  }
  
}