import { Component, Input } from '@angular/core';
import { DayOption, Tuple } from '../model/option';
import { isDateEquals } from '../model/utils';

@Component({
  selector: 'glam-days',
  templateUrl: './glam-days.component.html',
  styleUrls: [ './glam-days.component.scss', '../styles/core.scss' ]
})
export class GlamDaysComponent {

  @Input()
  set options(options: DayOption[]) {
    const chunks: DayOption[][] = [];

    for (let i = 0; i < options.length; i += 7) {
      chunks.push(options.slice(i, i + 7));
    }

    this.optionGroups = chunks;
  }

  @Input()
  value?: Date;

  protected optionGroups: DayOption[][] = [];

  protected getClass(option: DayOption): Tuple<boolean> {
    return {
      today: option.today,
      current: option.currentMonth,
      selected: isDateEquals(option.value, this.value),
    };
  }

}
