import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Revenue } from '../models/revenue';

const baseUrl = 'http://localhost:8080/api/v1/revenue';

@Injectable({
  providedIn: 'root'
})
export class RevenueService {

  constructor(private http: HttpClient) {}

  getRevenueByDay(date: string): Observable<any> {
    return this.http.get(`${baseUrl}/day?date=${date}`);
  }

  getRevenueByMonth(year: number, month: number): Observable<any> {
    return this.http.get(`${baseUrl}/month?year=${year}&month=${month}`);
  }

  getRevenueByYear(year: number): Observable<any> {
    return this.http.get(`${baseUrl}/year?year=${year}`);
  }
}
