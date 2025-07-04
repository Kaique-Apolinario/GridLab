import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  let token: string | null = null;

  // Makes sure Angular takes the browser's 'localStorage'
  if (typeof window !== 'undefined' && window.localStorage) {
    token = localStorage.getItem('accessToken'); // get token from localStorage
  }

    if (token && !(req.url.endsWith('/login') || req.url.endsWith('/register'))) {
      // clone the request and add Authorization header
    const authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    return next(authReq);
  }

  return next(req); // continue the request
};