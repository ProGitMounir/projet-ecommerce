import { HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';

export function authInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
  // Ajoutez les informations d'authentification
  const authReq = req.clone({
    setHeaders: {
      Authorization: 'Basic ' + btoa('user:password'), // Remplacez par vos identifiants
    },
  });
  return next(authReq);
}