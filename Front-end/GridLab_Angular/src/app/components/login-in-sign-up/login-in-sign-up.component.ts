import { Component, inject, OnInit, signal} from '@angular/core';
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
export class LoginInSignUpComponent implements OnInit{
  private fb: FormBuilder = inject(FormBuilder)
  private authService: AuthService = inject(AuthService)
  private router: Router= inject(Router)

  formTitle = signal("Register");


  loginForm: FormGroup = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmationPassword: [''],
    });


ngOnInit(): void {
  this.formTitle.set("Sign In")
}



onLogin() {
  this.emailNPasswordCheck();
  if (this.loginForm.valid){
      this.authService.login(this.loginForm.value).subscribe((res) => {
      localStorage.setItem('token', res.token);
      this.router.navigate(['/fileLib/' + res.userId]);
    });
  }
}


onRegister() {
  this.emailNPasswordCheck();
  if (this.loginForm.valid){
    if (this.loginForm.get('password')?.value === this.loginForm.get('confirmationPassword')?.value) {
      this.authService.register(this.loginForm.value).subscribe(() => {

      console.log("Current route I am on:",this.router.url);
      this.router.navigateByUrl('/',{skipLocationChange:true}).then(()=>{
      this.router.navigate(['/login'])})
      alert("You signed up! Log in to check your file library.");})
    } else {
      alert("Oops! Password and confirmation password doesn't match!")
    }}
}

emailNPasswordCheck(){
if ((this.loginForm.get('email')?.invalid && this.loginForm.get('email')?.touched) || this.loginForm.get('email')?.value == "") {
    alert("Oops! Please, insert a valid email.")
    }
    
  if ((this.loginForm.get('password')?.invalid && this.loginForm.get('password')?.touched) || this.loginForm.get('password')?.value == ""){
    alert("Oops! Your password needs to be at lease 6 characters long!" )
  }
}

 switchLoginOrSignUp(){
   if (this.formTitle() === "Sign In") {
     this.formTitle.set("Register");
   } else if (this.formTitle() === "Register") {
     this.formTitle.set("Sign In");
   }
   this.loginForm.reset();

 }

}