import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from "../models/user.model";
import { map, tap } from "rxjs/operators";
import { CookieService } from 'ngx-cookie-service';
import { LoginResponse } from "../models/login-response.model";
import { Router } from "@angular/router";
import { jwtDecode } from 'jwt-decode';

const baseUrl = 'http://localhost:3000/users';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private authApiUrl = 'http://localhost:8080/api/v1/auth';
  private tokenCookieName = 'jwt_token';
  
  constructor(private router: Router, private http: HttpClient, private cookieService: CookieService) {}

  loginOld(username: string, password:string): Observable<boolean> {
    return this.http
    .get<User[]>(`${baseUrl}?username=${username}&password=${password}`)
    .pipe(map((users) => users.length > 0),
    tap((isLoggedIn) => {
      if (isLoggedIn) {
        localStorage.setItem('isLoggedIn', 'true');
        localStorage.setItem('username', username);
      }
    })
    );
  }

  login(username: string, password: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const body = { username, password };

    return this.http.post<LoginResponse>(`${this.authApiUrl}/login`, body, { headers }).pipe(
      tap(response => {
        if (response && response.token) {
          this.storeTokenInCookie(response.token);
        }
      })
    )
  }

  storeTokenInCookie(token: string): void {
    const expirationTime = new Date();
    expirationTime.setHours(expirationTime.getHours() + 10);

    this.cookieService.set(this.tokenCookieName, token, expirationTime, '/');
  }

  getToken(): string | null {
    return this.cookieService.get(this.tokenCookieName);
  }

  getResource(resourceUrl: string): Observable<any> {
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${this.getToken()}` });
    return this.http.get<any>(`${this.authApiUrl}/${resourceUrl}`, { headers });
  }

  getUsername(): string | null {
    const token = this.getToken();
    if (token) {
      try {
        const decoded: any = jwtDecode(token);
        return decoded.sub;
      } catch (error) {
        return null;
      }
    }
    return null;
  }

  checkCredentials(): void {
    if (!this.getToken()) {
      this.router.navigate(['/login']);
    }
  }

  logout(): void {
    this.cookieService.delete(this.tokenCookieName, '/');
  }
}
