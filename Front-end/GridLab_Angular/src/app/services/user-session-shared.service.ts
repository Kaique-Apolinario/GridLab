import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserSessionSharedService {
  userId = signal(5);

  setUserId(id: number) {
    this.userId.set(id);
  }

  getUserId(): number {
    return this.userId();
  }
}
