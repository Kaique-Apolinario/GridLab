import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  private apiUrl = 'http://localhost:8080/login';
  private http:HttpClient = inject(HttpClient);

  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post(this.apiUrl, credentials);
  }
}