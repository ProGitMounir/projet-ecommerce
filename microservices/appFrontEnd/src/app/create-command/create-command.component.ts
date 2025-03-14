import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-create-command',
  imports: [ReactiveFormsModule, CommonModule, FormsModule],
  templateUrl: './create-command.component.html',
  styleUrls: ['./create-command.component.css']
})
export class CreateCommandComponent implements OnInit {
  commande: any = {
    produitId: null,
    utilisateurId: null,
    quantite: null,
    prixTotal: null,
    statut: 'EN_ATTENTE'
  };

  constructor(private http: HttpClient) {}

  ngOnInit(): void {}

  onSubmit(): void {
    this.http.post('http://localhost:8082/api/commandes', this.commande)
      .subscribe(
        (response) => {
          alert('Commande créée avec succès !');
          this.resetForm();
        },
        (error) => {
          console.error('Erreur lors de la création de la commande :', error);
          alert('Erreur lors de la création de la commande.');
        }
      );
  }

  resetForm(): void {
    this.commande = {
      produitId: null,
      utilisateurId: null,
      quantite: null,
      prixTotal: null,
      statut: 'EN_ATTENTE'
    };
  }
}