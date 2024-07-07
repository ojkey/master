import { Component, Input } from '@angular/core';
import { Option } from '../model/option';

@Component({
  selector: 'glam-select',
  templateUrl: './glam-select.component.html',
  styleUrls: [ './glam-select.component.scss', '../styles/core.scss' ]
})
export class GlamSelectComponent {

  @Input()
  options?: Option[];

  @Input()
  value: any;

  protected get label(): string | undefined {
    return this.options?.find(option => option.value === this.value)?.label;
  }
}
