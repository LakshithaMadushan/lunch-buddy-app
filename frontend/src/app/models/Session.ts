import {User} from "./User";

export interface Session{
  sessionId: number;
  startedAt: string;
  startedBy: string;
  endedAt: string;
  pickedLocation: string;
  startedUser: User;

}
