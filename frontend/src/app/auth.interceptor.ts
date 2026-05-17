import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // Sprawdza czy URL zawiera '/api/' (np. '/api/products' lub 'http://localhost:8080/api/products')
  if (!req.url.includes('/api/')) {
    return next(req);
  }

  const token = localStorage.getItem('token');

  // Jeśli mamy token i nagłówek nie został dodany ręcznie, wstrzykujemy go
  if (token && !req.headers.has('Authorization')) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(req);
};
