import {Injectable} from '@angular/core';
import {TokenService} from "./token-service.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {SignInRequest} from "../../models/SignInRequest";
import {JwtTokenResponse} from "../../models/JwtTokenResponse";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(private tokenService: TokenService, private httpClient: HttpClient) {
  }

  login(signInRequest: SignInRequest): Observable<any> {
    return  this.httpClient
      .post<JwtTokenResponse>(`${environment.BASE_API_URL}/sign-in`, signInRequest, this.httpOptions);
  }

  signOut(): void {
    this.tokenService.removeToken();
  }

  isLoggedIn(): boolean {
    return !!this.tokenService.getToken();
  }
}
