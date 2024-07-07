import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { GlamCalendarModule } from 'glam-calendar';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ RouterOutlet, GlamCalendarModule ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'demo';
}
