import { Component, HostBinding, Input } from '@angular/core';
import { GlamSize } from '../model';

@Component({
  selector: 'glam-button',
  templateUrl: './glam-button.component.html',
  styleUrls: [ './glam-button.component.scss']
})
export class GlamButtonComponent {

  @Input()
  label?: string;

  @Input()
  color = '#6750A4';

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
