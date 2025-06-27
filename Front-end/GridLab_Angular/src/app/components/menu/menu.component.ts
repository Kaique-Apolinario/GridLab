import { Component, inject, OnInit, signal } from '@angular/core';
import { UserSessionSharedService } from '../../services/user-session-shared.service';

@Component({
  selector: 'app-menu',
  imports: [],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit{
  private sessionService: UserSessionSharedService = inject(UserSessionSharedService);
  userId = signal("");

ngOnInit(): void {
  this.userId.set("/fileLib/" + this.sessionService.getUserId());
}
}
