import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Subscription} from "rxjs";
import {UserService} from "../../services/users/user.service";
import {User} from "../../models/User";

@Component({
  selector: 'app-add-members',
  templateUrl: './add-members.component.html',
  styleUrls: ['./add-members.component.scss']
})
export class AddMembersComponent implements OnInit, OnDestroy {
  @Input({required: true})
  actionButtonName!: string;
  subscriptions: Subscription[] = [];

  @Output('onClickCancel')
  clickCancelEmitter = new EventEmitter<boolean>();

  @Output('emitUserList')
  userListEventEmitter = new EventEmitter<Array<User>>();

  @Output('emitNewlySelectedUserList')
  newlySelectedUserListEventEmitter = new EventEmitter<Array<User>>();
  userList: Array<User> = [];

  @Input({required: false})
  alreadySelectedUserIDs: Array<string> = [];

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    let sub = this.userService.getAllUsers().subscribe({
      next: (res: any) => {
        this.userList = res as Array<User>;
        this.alreadySelectedUserIDs.length > 0 && this.userList.map(user => {
          if (this.alreadySelectedUserIDs.includes(user.userId)) user.available = false;
        });
      }
    });
    this.subscriptions.push(sub);
  }

  cancelButton() {
    this.clickCancelEmitter.emit(true);
  }

  actionButton() {
    this.userListEventEmitter.emit(this.userList);
    let users = this.userList.filter(user => user._selected);
    this.newlySelectedUserListEventEmitter.emit(users);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => {
      sub.unsubscribe();
    })
  }

  addUser(user: User | undefined, e: Event) {
    if (user) {
      user._selected = (e?.target && (e.target as HTMLInputElement).checked) ?? false;
    }
  }
}
