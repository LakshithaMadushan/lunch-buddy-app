import {Injectable, NgZone} from '@angular/core';
import {Observable} from "rxjs";
import {TokenService} from "./auth/token-service.service";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class EventSourceService {
  constructor(private tokenService: TokenService,
              private ngZone: NgZone,
              private router: Router) {
  }

  getServerSentEvents(url: string): Observable<MessageEvent> {
    return new Observable<MessageEvent>((observer) => {
      const token = this.tokenService.getToken();
      const eventSource = new EventSource(`${url}?token=${token}`);

      eventSource.onmessage = (event) => {
        observer.next(event);
      };

      eventSource.onerror = (error) => {
        this.ngZone.run(() => {
          this.router.navigate(['/sign-in']).then();
        });
        observer.error(error);
      };

      return () => {
        eventSource.close();
      };
    });
  }
}
