import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  private apiUrl = 'http://localhost:8080';
  private http:HttpClient = inject(HttpClient);

  login(loginCredentials: { email: string; password: string }): Observable<any> {
    return this.http.post(this.apiUrl + "/login", loginCredentials).pipe(
     catchError(error => {
      alert(error.error)
      throw error;
     })
  )}

  register(loginCredentials: { email: string; password: string; confirmationPassword: string }): Observable<any> {
    return this.http.post(this.apiUrl + "/register", loginCredentials).pipe(
     catchError(error => {
      alert(error.error)
      throw error;
     })
  )}

  
  logout(): Observable<any> {
    return this.http.post(this.apiUrl + "/logout", {});
  }
}