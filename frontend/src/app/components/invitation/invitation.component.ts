import {Component, Input, OnInit} from '@angular/core';
import {Invitation} from "../../models/Invitation";
import {User} from "../../models/User";
import {CommonConstants} from "../../utils/common-constants";
import {Router} from "@angular/router";
import {SessionService} from "../../services/sessions/session.service";
import {Subscription} from "rxjs";
import {InvitationService} from "../../services/invitations/invitation.service";
import {NotificationService} from "../../services/notification.service";

@Component({
  selector: 'app-invitation',
  templateUrl: './invitation.component.html',
  styleUrls: ['./invitation.component.scss']
})
export class InvitationComponent implements OnInit {

  protected readonly InvitationStatus = CommonConstants.INVITATION_STATUS;

  subscriptions: Subscription[] = [];

  @Input({required: true})
  invitation: Invitation | undefined;
  startedUser: User | undefined;

  constructor(private router: Router,
              private sessionService: SessionService,
              private invitationService: InvitationService,
              private notificationService: NotificationService) {
  }

  ngOnInit(): void {
    this.startedUser = this.invitation?.session?.startedUser;
  }

  goToTheSession() {
    console.log("goToTheSession");
    this.router.navigate(['/session', this.invitation?.session?.sessionId]).then(r => {
    });
  }

  leaveFromSession(e: MouseEvent) {
    this.invitation?.session.sessionId && this.invitationService
      .leaveInvitation(this.invitation?.session.sessionId)
      .subscribe({
        next: value => {
          this.notificationService.success("Leave form the session");
        }
      });
    e.stopPropagation();
  }

  acceptInvitation(e: MouseEvent) {
    this.invitation?.session.sessionId && this.invitationService
      .acceptInvitation(this.invitation?.session.sessionId)
      .subscribe({
        next: value => {
          this.notificationService.success("Invitation accepted");
        }
      });
    e.stopPropagation();
  }

  rejectInvitation(e: MouseEvent) {
    this.invitation?.session.sessionId && this.invitationService
      .rejectInvitation(this.invitation?.session.sessionId)
      .subscribe({
        next: value => {
          this.notificationService.success("Invitation rejected");
        }
      });
    e.stopPropagation();
  }

  endSession(e: MouseEvent) {
    console.log("leaveFromSession");
    e.stopPropagation();

    let sub = this.sessionService.endSession(this.invitation?.session.sessionId!).subscribe({
      next: (res: any) => {
        console.log(res);
      }
    });
    this.subscriptions.push(sub);
  }

  isJoined() {
    return this.invitation?.invitationStatus === CommonConstants.INVITATION_STATUS.ACCEPTED
      || this.invitation?.invitationStatus === CommonConstants.INVITATION_STATUS.OWNER;
  }

  isEnded() {
    return this.invitation?.session?.endedAt?.length! > 0;
  }

  isOwner() {
    return this.invitation?.invitationStatus === CommonConstants.INVITATION_STATUS.OWNER;
  }

  getSessionStatus() {
    let session = this.invitation?.session;
    if (this.isEnded()) {
      return 'Session Ended - ' + (session?.pickedLocation ? session?.pickedLocation : 'no Location') + ' was picked';
    } else {
      return 'Session Started';
    }
  }
}
