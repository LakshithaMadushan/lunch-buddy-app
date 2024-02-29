import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {Invitation} from "../../models/Invitation";
import {Subscription} from "rxjs";
import {InvitationService} from "../../services/invitations/invitation.service";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../../models/User";
import {SessionService} from "../../services/sessions/session.service";
import {EventSourceService} from "../../services/event-source.service";
import {environment} from "../../../environments/environment";
import {LoaderService} from "../../services/loader.service";
import {NotificationService} from "../../services/notification.service";
import {TokenService} from "../../services/auth/token-service.service";
import {JwtService} from "../../services/auth/jwt-service.service";

@Component({
  selector: 'app-session',
  templateUrl: './session.component.html',
  styleUrls: ['./session.component.scss']
})
export class SessionComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  sessionId: string | null | undefined
  members: Array<Invitation> = [];
  showAddMembersModal: boolean = false;
  alreadyInvitedUsers: Array<string> = [];
  location: string = '';
  enableEndSession = false;
  startedBy: string | undefined;
  currentUserId: string | undefined;
  isEndedSession = false;

  constructor(private sessionService: SessionService,
              private eventSourceService: EventSourceService,
              private invitationService: InvitationService,
              private activatedRoute: ActivatedRoute,
              private loaderService: LoaderService,
              private cdr: ChangeDetectorRef,
              private notificationService: NotificationService,
              private router: Router,
              private tokenService: TokenService,
              private jwtService: JwtService) {
    activatedRoute.paramMap.subscribe(paramMap => {
      this.sessionId = paramMap.get('sessionId');
    });

    let token = tokenService.getToken();
    if (token) {
      let decodeToken = jwtService.decodeToken(token);
      this.currentUserId = decodeToken?.sub;
    }
  }

  ngOnInit(): void {
    this.loaderService.show();
    setTimeout(() => {
      this.loaderService.hide();
    }, 2000);

    let sub = this.eventSourceService.getServerSentEvents(`${environment.BASE_API_URL}/sse/invitation/session/${this.sessionId}`).subscribe(
      (event: MessageEvent) => {
        this.members = JSON.parse(event.data) as Array<Invitation>;
        this.alreadyInvitedUsers = this.members.map(invitations => invitations.user.userId);
        let temp = this.members.some(invitations => invitations.location && invitations.location !== '');
        this.enableEndSession = temp && (this.startedBy === this.currentUserId);
        this.startedBy = (this.members?.length > 0) ? this.members[0].session.startedBy : undefined;
        this.isEndedSession = (this.members?.length > 0 && !!this.members[0].session.endedAt);

        this.cdr.detectChanges();
      },
      error => this.router.navigate(['/home']).then()
    );
    this.subscriptions.push(sub);
  }

  loadInvitations() {
    let sub = this.invitationService.getInvitationListForSession(this.sessionId ?? '').subscribe({
      next: (res: any) => {
        this.members = res as Array<Invitation>
        this.alreadyInvitedUsers = this.members.map(invitations => invitations.user.userId);
      },
      error: () => {
        this.router.navigate(['home']).then();
      }
    });
    this.subscriptions.push(sub);
  }

  closeAddNewMembersModal() {
    this.showAddMembersModal = false;
  }

  addMembers(userList: Array<User>) {
    let invitationList: any = [];
    userList
      .forEach(user => invitationList.push({
        invitationId: {
          userId: user.userId
        }
      }));
    let session = {
      invitations: invitationList
    };

    if (!this.sessionId) return;
    let sub = this.sessionService.addUsersToSession(this.sessionId, session).subscribe({
      next: (res: any) => {
        this.closeAddNewMembersModal();
        this.loadInvitations();
      }
    });
    this.subscriptions.push(sub);
  }

  trackByFn(index: number, item: any): any {
    return index; // or unique item identifier
  }

  setLocation(event: any) {
    this.location = (event.target.value) ? (event.target.value) : '';
  }

  addLocation() {
    this.invitationService.updateLocation(this.sessionId!, this.location).subscribe({
      next: value => console.log(value)
    });
  }

  endSession() {
    this.notificationService.danger("Please add location first");
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => {
      sub.unsubscribe();
    })
  }
}
