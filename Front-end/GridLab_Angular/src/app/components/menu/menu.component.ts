import { Component, inject, OnInit, signal } from '@angular/core';
import { UserSessionSharedService } from '../../services/user-session-shared.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-menu',
  imports: [],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit{
  private sessionService: UserSessionSharedService = inject(UserSessionSharedService);
  private authService: AuthService = inject(AuthService);

  userId = signal("");

ngOnInit(): void {
  this.sessionService.userId$.subscribe((id) => {
    if (id !== undefined){
    this.userId.set("/fileLib/" + id);
  }})
}

logout() {
    localStorage.removeItem('acessToken');
    this.authService.logout().subscribe();
  }
}
