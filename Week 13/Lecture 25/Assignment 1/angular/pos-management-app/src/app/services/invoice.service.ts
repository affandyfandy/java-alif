import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Invoice } from '../models/invoice';

const baseUrl = 'http://localhost:8080/api/v1/invoices';
const baseUrlProduct = 'http://localhost:8080/api/v1/invoice-products';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {
  constructor(private http : HttpClient) {}

  // getInvoices(): Observable<Invoice[]> {
  //   return this.http.get<any>(baseUrl)
  //     .pipe(map(response => response.content));
  // }

  getInvoices(): Observable<Invoice[]> {
    return this.http.get<any>(baseUrl + "/list")
      .pipe(map(response => response));
  }

  get(id: any): Observable<any> {
    return this.http.get(`${baseUrl}/${id}`);
  }

  update(invoiceId: any, productId: any, data: any): Observable<any> {
    return this.http.put(`${baseUrlProduct}/${invoiceId}/${productId}`, data);
  }

  create(data: any): Observable<any> {
    return this.http.post(baseUrl, data);
  }

  deleteInvoice(id: any): Observable<void> {
    return this.http.delete<void>(`${baseUrl}/${id}`);
  }

  exportInvoiceToExcel(customerId: string, month: number, year: number): Observable<Blob> {
    let params = new HttpParams();

    if (customerId) {
      params = params.set('customerId', customerId);
    }

    if (month) {
      params = params.set('month', month.toString());
    }

    if (year) {
      params = params.set('year', year.toString());
    }

    return this.http.get(`${baseUrl}/excel`, { params, responseType: 'blob' });
  }

  exportInvoiceToPdf(id: any): Observable<Blob> {
    return this.http.get(`${baseUrl}/${id}/pdf`, { responseType: 'blob' });
  }
}
