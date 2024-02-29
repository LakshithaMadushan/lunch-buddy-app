import {Session} from "./Session";
import {User} from "./User";

export interface Invitation {
  fullName: string;
  avatarUrl: string;
  designation: string;
  joined: boolean;

  invitationStatus: string;
  location: string;

  session: Session;
  user: User;

  isJoined: boolean;
}

