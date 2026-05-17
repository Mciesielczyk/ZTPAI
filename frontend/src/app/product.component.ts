import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from './product.service';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product.component.html',
})
export class ProductComponent implements OnInit {
  @Output() loggedOut = new EventEmitter<void>();

  products: any[] = [];
  name = '';
  price = 0;
  description = '';
  isAdmin = false;
  message = '';
  isError = false;

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.checkAdminRole();
    this.loadProducts();
  }

  private extractErrorMessage(err: any, fallback: string): string {
    const response = err?.error;

    if (!response) {
      return fallback;
    }

    if (typeof response === 'string') {
      return response;
    }

    if (response.message) {
      return response.message;
    }

    if (Array.isArray(response.errors) && response.errors.length > 0) {
      return response.errors
        .map((item: any) => item?.defaultMessage || item?.message || JSON.stringify(item))
        .join(', ');
    }

    return fallback;
  }

  loadProducts() {
    this.productService.getProducts().subscribe({
      next: (data) => {
        this.products = data;
      },
      error: (err) => {
        this.isError = true;
        this.message = this.extractErrorMessage(err, 'Nie udało się pobrać produktów.');
        console.error('Nie udało się pobrać produktów', err);
      }
    });
  }

  checkAdminRole() {
    const token = localStorage.getItem('token');

    if (!token) {
      this.isAdmin = false;
      return;
    }

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const role = payload.role || payload.roles || '';
      this.isAdmin = String(role).includes('ADMIN');
    } catch {
      this.isAdmin = false;
    }
  }

  onAddProduct() {
    const productPayload = {
      name: this.name.trim(),
      price: Number(this.price),
      description: this.description.trim()
    };

    this.productService.createProduct(productPayload).subscribe({
      next: () => {
        this.isError = false;
        this.message = 'Produkt został pomyślnie dodany!';
        this.loadProducts();
        this.name = '';
        this.price = 0;
        this.description = '';
      },
      error: (err) => {
        this.isError = true;
        this.message = this.extractErrorMessage(err, 'Błąd! Brak uprawnień lub niepoprawne dane.');
      }
    });
  }

  logout() {
    localStorage.removeItem('token');
    this.loggedOut.emit();
  }
}
