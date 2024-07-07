import { Component, HostBinding, Input } from '@angular/core';
import { GlamSize } from '../model';


@Component({
  selector: 'glam-icon-button',
  templateUrl: './glam-icon-button.component.html',
  styleUrls: [ './glam-icon-button.component.scss']
})
export class GlamIconButtonComponent {

  @Input() icon?: string;

  @Input()
  set size(value: GlamSize) {
    this.styleClasses = this.getStyleClasses(value);
  }

  @HostBinding('class')
  protected styleClasses = '';

  private getStyleClasses(size?: GlamSize): string {
    return size || 'regular';
  }
}
