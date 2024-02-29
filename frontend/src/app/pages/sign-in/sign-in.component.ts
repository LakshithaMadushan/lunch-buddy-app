import {Component} from '@angular/core';
import {AuthService} from "../../services/auth/auth-service.service";
import {TokenService} from "../../services/auth/token-service.service";
import {Router} from "@angular/router";
import {NotificationService} from "../../services/notification.service";

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent {
  userId: string | undefined;
  password: string | undefined;

  constructor(private tokenService: TokenService,
              private authService: AuthService,
              private notificationService: NotificationService,
              private router: Router) {
  }

  setUserId(event: any) {
    this.userId = (event.target.value);
  }

  setPassword(event: any) {
    this.password = (event.target.value);
  }

  signIn() {
    if (!this.userId && !this.password) return;
    this.authService.login({userId: this.userId!, password: this.password!})
      .subscribe({
        next: value => {
          this.tokenService.setToken(value.token);
          this.router.navigate(['/home']).then();
        },
        error: err => {
          this.notificationService.danger("Invalid username or password");
        }
      });
  }
}
