import {Component} from '@angular/core';
import {NotificationService} from "../../services/notification.service";
import {Observable} from "rxjs";
import {CommonConstants} from "../../utils/common-constants";

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent {
  notificationStatuses = CommonConstants.NOTIFICATION_STATUS;
  notification$: Observable<{ type: string, message: string } | undefined>;

  constructor(private notificationService: NotificationService) {
    this.notification$ = notificationService.notificationSubject;
  }
}
