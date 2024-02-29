import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {catchError, finalize, Observable, throwError} from 'rxjs';
import {LoaderService} from "./loader.service";
import {TokenService} from "./auth/token-service.service";
import {Router} from "@angular/router";

@Injectable({
  providedIn: "root"
})
export class CustomInterceptor implements HttpInterceptor {

  constructor(private loaderService: LoaderService, private tokenService: TokenService, private router: Router) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    this.loaderService.show();
    const token = this.tokenService.getToken();

    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 403) {
          // Handling 403 Forbidden error here
          // Redirecting to a login page
          this.router.navigate(['sign-in']).then();
        }
        return throwError(error);
      }),
      finalize(() => {
        this.loaderService.hide();
      })
    );
  }
}
