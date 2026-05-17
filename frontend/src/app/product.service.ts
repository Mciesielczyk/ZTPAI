import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = '/api/products';

  constructor(private http: HttpClient) {}

  getProducts(): Observable<any[]> {
    const token = localStorage.getItem('token');

    return this.http.get<any[]>(this.apiUrl, {
      headers: token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined
    });
  }

  createProduct(productData: any): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = token
      ? new HttpHeaders({
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        })
      : new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post(this.apiUrl, productData, { headers });
  }
}
