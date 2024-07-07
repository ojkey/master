import { Component, HostBinding, Input } from '@angular/core';
import { GlamSize } from '../model';


@Component({
  selector: 'glam-icon-button',
  templateUrl: './glam-icon-button.component.html',
  styleUrls: [ './glam-icon-button.component.scss', '../styles/core.scss']
})
export class GlamIconButtonComponent {

  @Input() icon?: string;

  @HostBinding('class.size')
  protected size: GlamSize = 'regular';
}
