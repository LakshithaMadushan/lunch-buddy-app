import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(private httpClient: HttpClient) {
  }

  createSession(session: any) {
    return this.httpClient.post(`${environment.BASE_API_URL}/session`, session, this.httpOptions)
  }

  addUsersToSession(sessionId: string, session: any) {
    return this.httpClient.post(`${environment.BASE_API_URL}/session/addUsers/${sessionId}`, session, this.httpOptions)
  }

  endSession(sessionId: number) {
    return this.httpClient.put(`${environment.BASE_API_URL}/session/${sessionId}`, this.httpOptions)
  }
}
