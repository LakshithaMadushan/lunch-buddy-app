import {Component} from '@angular/core';
import {JwtService} from "../../services/auth/jwt-service.service";
import {TokenService} from "../../services/auth/token-service.service";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth/auth-service.service";

@Component({
  selector: 'app-logged-user',
  templateUrl: './logged-user.component.html',
  styleUrls: ['./logged-user.component.scss']
})
export class LoggedUserComponent {
  fullName = '';
  avatarUrl = '/assets/images/avatar/empty.jpg';

  constructor(private jwtService: JwtService,
              private tokenService: TokenService,
              private router: Router,
              private authService: AuthService) {
    let token = tokenService.getToken();
    if (token) {
      let decodeToken = jwtService.decodeToken(token);
      this.fullName = `${decodeToken?.firstName} ${decodeToken?.lastName}`;
      this.avatarUrl = decodeToken?.avatarUrl ?? '/assets/images/avatar/empty.jpg';
    }
  }

  signOut() {
    this.authService.signOut();
    this.router.navigate(['/']).then();
  }
}
