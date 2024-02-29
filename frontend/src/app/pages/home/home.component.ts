import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {Invitation} from "../../models/Invitation";
import {InvitationService} from "../../services/invitations/invitation.service";
import {Subscription} from "rxjs";
import {CommonConstants} from "../../utils/common-constants";
import {User} from "../../models/User";
import {SessionService} from "../../services/sessions/session.service";
import {Router} from "@angular/router";
import {EventSourceService} from "../../services/event-source.service";
import {environment} from "../../../environments/environment";
import {TokenService} from "../../services/auth/token-service.service";
import {JwtService} from "../../services/auth/jwt-service.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  invitations: Array<Invitation> = [];
  showAddMembersModal: boolean = false;
  isOwningASession = false;
  currentUserId!: string;

  constructor(private invitationService: InvitationService,
              private sessionService: SessionService,
              private eventSourceService: EventSourceService,
              private cdr: ChangeDetectorRef,
              private router: Router,
              private tokenService: TokenService,
              private jwtService: JwtService) {
    let token = tokenService.getToken();
    if (token) {
      let decodeToken = jwtService.decodeToken(token);
      this.currentUserId = decodeToken?.sub ? decodeToken?.sub : '';
    }
  }

  ngOnInit(): void {
    let sub = this.eventSourceService.getServerSentEvents(`${environment.BASE_API_URL}/sse/invitation/user`).subscribe(
      (event: MessageEvent) => {
        this.invitations = JSON.parse(event.data) as Array<Invitation>;
        this.isOwningASession = this.invitations.some(value => value.invitationStatus === CommonConstants.INVITATION_STATUS.OWNER);
        this.cdr.detectChanges();
      }
    );

    this.subscriptions.push(sub);
  }

  closeAddNewMembersModal() {
    this.showAddMembersModal = false;
  }

  startNewSession() {
    this.showAddMembersModal = true;
  }

  startSession(userList: Array<User>) {
    let invitationList = [];
    invitationList.push({
      invitationStatus: CommonConstants.INVITATION_STATUS.OWNER
    });
    userList
      .filter(u => u._selected)
      .forEach(user => invitationList.push({
        invitationId: {
          userId: user.userId
        },
        invitationStatus: CommonConstants.INVITATION_STATUS.PENDING
      }));
    let session = {
      invitations: invitationList
    };

    let sub = this.sessionService.createSession(session).subscribe({
      next: (res: any) => {
        this.router.navigate(['/session', res.sessionId]).then();
      }
    });
    this.subscriptions.push(sub);
  }

  trackByFn(index: number, item: any): any {
    return index; // or unique item identifier
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => {
      sub.unsubscribe();
    })
  }
}
