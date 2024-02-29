import {Injectable} from '@angular/core';
import {DecodedToken} from "../../models/DecodedToken";

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  constructor() {
  }

  decodeToken(token: string): DecodedToken | null {
    try {
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
       // Cast to DecodedToken interface
      return JSON.parse(atob(base64)) as DecodedToken;
    } catch (error) {
      console.error('Error decoding token:', error);
      return null;
    }
  }
}
