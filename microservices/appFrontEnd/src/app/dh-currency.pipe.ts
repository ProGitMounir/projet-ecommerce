import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dhCurrency'
})
export class DhCurrencyPipe implements PipeTransform {
  transform(value: number): string {
    if (isNaN(value)) {
      return '';
    }
    // Formate le nombre avec deux décimales et ajoute "DH"
    return `${value.toFixed(2)} DH`;
  }
}