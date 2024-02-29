import {Component, Input, OnInit} from '@angular/core';
import {Invitation} from "../../models/Invitation";

@Component({
  selector: 'app-section-card',
  templateUrl: './section-card.component.html',
  styleUrls: ['./section-card.component.scss']
})
export class SectionCardComponent implements OnInit{
  @Input({required: true})
  member: Invitation | undefined;

  ngOnInit(): void {
  }
}
