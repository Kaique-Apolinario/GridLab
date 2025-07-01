import { Injectable, signal } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';

@Injectable({
  providedIn: 'root'
})
export class UserSessionSharedService {
  
  private userIdSubject = new BehaviorSubject<number | undefined>(
    Number(localStorage.getItem('userId')) || undefined
  );

  userId$ = this.userIdSubject.asObservable();

  setUserId(id: number) {
    localStorage.setItem('userId', String(id)); // Save to localStorage
    this.userIdSubject.next(id); // Notify subscribers
  }

  getUserId(): number | undefined {
    return this.userIdSubject.getValue();
  }
}