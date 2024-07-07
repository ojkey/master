import { Component, Input } from '@angular/core';
import { Option } from '../model/option';

@Component({
  selector: 'glam-weekdays',
  templateUrl: './glam-weekdays.component.html',
  styleUrls: [ './glam-weekdays.component.scss', '../styles/core.scss']
})
export class GlamWeekdaysComponent {

  @Input()
  options?: Option[];
}
