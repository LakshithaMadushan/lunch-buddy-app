import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoaderService {
  isLoading = new BehaviorSubject<number>(0);

  constructor() {
  }

  show() {
    this.isLoading.next(this.isLoading.value + 1);
  }

  hide() {
    this.isLoading.next(this.isLoading.value - 1);
  }
}
