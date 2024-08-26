import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

const baseUrl = 'http://localhost:3000/products';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getProducts(): Observable<any[]> {
    return this.http.get<any[]>(baseUrl);
  }

  getProductById(id: string): Observable<any> {
    return this.http.get<any>(`${baseUrl}/${id}`);
  }

  addProduct(product: any): Observable<any> {
    return this.http.post(baseUrl, product);
  }

  updateProduct(product: any): Observable<any> {
    return this.http.put(`${baseUrl}/${product.id}`, product);
  }

  deleteProduct(id: string): Observable<any> {
    return this.http.delete(`${baseUrl}/${id}`);
  }

  generateId(): Observable<string> {
    return this.getProducts().pipe(
      map(products => {
        const maxId = products.length ? Math.max(...products.map(p => +p.id)) : 0;
        return (maxId + 1).toString();
      })
    );
  }
}
