import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class InvitationService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(private httpClient: HttpClient) {
  }

  getInvitationListForUser() {
    return this.httpClient.get(`${environment.BASE_API_URL}/invitation/user`, this.httpOptions);
  }

  getInvitationListForSession(sessionId: string) {
    return this.httpClient.get(`${environment.BASE_API_URL}/invitation/session/${sessionId}`, this.httpOptions);
  }

  updateLocation(sessionId: string, location: string) {
    return this.httpClient.patch(`${environment.BASE_API_URL}/invitation/updateLocation/${sessionId}/${location}`, this.httpOptions);
  }

  acceptInvitation(sessionId: number) {
    return this.httpClient.patch(`${environment.BASE_API_URL}/invitation/accept/${sessionId}`, this.httpOptions);
  }

  rejectInvitation(sessionId: number) {
    return this.httpClient.patch(`${environment.BASE_API_URL}/invitation/reject/${sessionId}`, this.httpOptions);
  }

  leaveInvitation(sessionId: number) {
    return this.httpClient.patch(`${environment.BASE_API_URL}/invitation/leave/${sessionId}`, this.httpOptions);
  }
}
