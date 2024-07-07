import { Component, Input } from '@angular/core';
import { GlamCalendarService } from '../glam-calendar.service';

@Component({
  selector: 'glam-calendar-panel',
  templateUrl: './glam-calendar-panel.component.html',
  styleUrls: ['./glam-calendar-panel.component.scss', '../styles/core.scss']
})
export class GlamCalendarPanelComponent {

  @Input()
  value?: Date;

  constructor(protected service: GlamCalendarService) {
  }
}
