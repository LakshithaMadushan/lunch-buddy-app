import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {CommonConstants} from "../utils/common-constants";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  notificationSubject = new Subject<{ type: string, message: string } | undefined>();

  constructor() {
  }

  // Method to trigger info notification
  info(message: string) {
    this.showNotification(CommonConstants.NOTIFICATION_STATUS.INFO, message);
  }

  // Method to trigger danger notification
  danger(message: string) {
    this.showNotification(CommonConstants.NOTIFICATION_STATUS.DANGER, message);
  }

  // Method to trigger success notification
  success(message: string) {
    this.showNotification(CommonConstants.NOTIFICATION_STATUS.SUCCESS, message);
  }

  // Method to trigger warning notification
  warning(message: string) {
    this.showNotification(CommonConstants.NOTIFICATION_STATUS.WARNING, message);
  }

  // Method to show notification with timeout
  private showNotification(type: string, message: string) {
    this.notificationSubject.next({type, message});

    // Hide notification after 5 seconds
    setTimeout(() => {
      this.notificationSubject.next(undefined);
    }, 5000);
  }

  // Method to subscribe to notifications
  getNotification() {
    return this.notificationSubject.asObservable();
  }
}
