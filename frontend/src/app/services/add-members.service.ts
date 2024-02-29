import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AddMembersService {

  showAddMembersModal: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor() {}

  hideModal() {
    this.showAddMembersModal.next(false);
  }

  showModal() {
    this.showAddMembersModal.next(true);
  }
}
