import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { GlamCalendarComponent } from 'glam-calendar';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ RouterOutlet, GlamCalendarComponent ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'demo';
}
