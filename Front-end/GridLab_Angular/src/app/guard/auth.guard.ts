import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { Router } from '@angular/router';
import {jwtDecode, JwtPayload} from 'jwt-decode';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  let token: string | null = null;

  // Makes sure Angular takes the browser's 'localStorage'
  if (typeof window !== 'undefined' && window.localStorage) {
    token = localStorage.getItem('token'); // get token from localStorage
  }

  //If the user has a token
  if (token){
    // Verify whether it's expired or not
    const decodedToken = jwtDecode<JwtPayload>(token);
    const currentTime = Math.floor(Date.now() / 1000);

    if (decodedToken.exp && decodedToken.exp < currentTime) {
      localStorage.removeItem('token');
      router.navigate(['/login']);
      alert("Expired token! Please, log in.")
      return false
    }
    return true;
  }


  //Redirects to "/login" if the user don't have a token
  if (!token) {
    router.navigate(['/login']);
    alert("Please, log in to access your file library.")
    return false;
  }
  return true;
};
