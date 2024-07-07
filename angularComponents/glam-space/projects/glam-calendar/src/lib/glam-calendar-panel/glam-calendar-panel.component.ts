import { Component } from '@angular/core';
import { Option } from '../model/option';
import { GlamCalendarService } from '../glam-calendar.service';

@Component({
  selector: 'glam-calendar-panel',
  templateUrl: './glam-calendar-panel.component.html',
  styleUrls: ['./glam-calendar-panel.component.scss', '../styles/core.scss']
})
export class GlamCalendarPanelComponent {

  constructor(protected service: GlamCalendarService) {
  }
}
