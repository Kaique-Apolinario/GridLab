import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login-in-sign-up',
  imports: [ReactiveFormsModule, CommonModule],
  standalone: true,
  templateUrl: './login-in-sign-up.component.html',
  styleUrl: './login-in-sign-up.component.scss'
})
export class LoginInSignUpComponent {
  private fb: FormBuilder = inject(FormBuilder)
  private authService: AuthService = inject(AuthService)
  private router: Router= inject(Router)
      
  loading = false;
  error: string | null = null;
      
  loginForm: FormGroup = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });;

  onSubmit() {
    if (this.loginForm.invalid) return;

    this.loading = true;
    this.error = null;

    this.authService.login(this.loginForm.value).subscribe((res) => {
        localStorage.setItem('token', res.token);
        this.router.navigate(['/fileLib']);
  });
  }
}