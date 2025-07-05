import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class UserSessionSharedService {
  private localStorageObject: number | undefined;
  private userIdSubject: BehaviorSubject<number | undefined>;
  public userId$: Observable<number | undefined>;

  constructor(){
    if (typeof window !== 'undefined' && window.localStorage) {
    this.localStorageObject = Number(localStorage.getItem('userId')) || undefined;
  }
    this.userIdSubject = new BehaviorSubject<number | undefined>(this.localStorageObject);
    
     this.userId$ = this.userIdSubject.asObservable();
  }


  setUserId(id: number) {
    localStorage.setItem('userId', String(id)); // Save the user Id to localStorage
    this.userIdSubject.next(id); // Notify subscribers
  }

  getUserId(): number | undefined {
    return this.userIdSubject.getValue();
  }
  
}