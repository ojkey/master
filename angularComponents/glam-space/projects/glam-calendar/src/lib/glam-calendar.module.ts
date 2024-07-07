import { NgModule } from '@angular/core';
import { GlamCalendarInputComponent } from './glam-calendar-input/glam-calendar-input.component';
import { GlamCalendarPanelComponent } from './glam-calendar-panel/glam-calendar-panel.component';
import { GlamSelectComponent } from './glam-select/glam-select.component';
import { GlamSelectPanelComponent } from './glam-select-panel/glam-select-panel.component';
import { GlamWeekdaysComponent } from './glam-weekdays/glam-weekdays.component';
import { NgClass, NgForOf, NgIf } from '@angular/common';
import { GlamDaysComponent } from './glam-days/glam-days.component';
import { GlamButtonComponent, GlamIconButtonComponent } from './components';

const components = [
  GlamCalendarInputComponent,
  GlamCalendarPanelComponent,
  GlamSelectComponent,
  GlamSelectPanelComponent,
  GlamWeekdaysComponent,
  GlamDaysComponent,
  // Components
  GlamButtonComponent,
  GlamIconButtonComponent,
];

@NgModule({
  declarations: components,
  imports: [
    NgForOf,
    NgClass,
    NgIf
  ],
  exports: components
})
export class GlamCalendarModule { }

