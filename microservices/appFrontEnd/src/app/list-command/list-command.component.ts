import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { DhCurrencyPipe } from '../dh-currency.pipe';

@Component({
  selector: 'app-list-command',
  imports: [ReactiveFormsModule, CommonModule, FormsModule, DhCurrencyPipe],
  templateUrl: './list-command.component.html',
  styleUrls: ['./list-command.component.css']
})
export class ListCommandComponent implements OnInit {
  commandeList: any[] = []; // Liste des commandes

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadCommandes(); // Charger les commandes au démarrage du composant
  }

  // Charger la liste des commandes depuis le backend
  loadCommandes(): void {
    this.http.get<any[]>('http://localhost:8082/api/commandes').subscribe(
      (data) => {
        this.commandeList = data; // Mettre à jour la liste des commandes
      },
      (error) => {
        console.error('Erreur lors du chargement des commandes :', error);
      }
    );
  }

  // Valider une commande
  validerCommande(commandeId: number): void {
    this.http.put(`http://localhost:8082/api/commandes/${commandeId}/valider`, {}).subscribe(
      () => {
        alert('Commande validée avec succès !');
        this.loadCommandes(); // Recharger la liste des commandes après validation
      },
      (error) => {
        console.error('Erreur lors de la validation de la commande :', error);
        alert('Erreur lors de la validation de la commande.');
      }
    );
  }
}