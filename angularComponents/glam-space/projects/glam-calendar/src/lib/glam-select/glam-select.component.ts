import { Component, EventEmitter, Input, Output } from '@angular/core';
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

  @Output()
  openPanel = new EventEmitter<MouseEvent>();

  @Output()
  valueChanges = new EventEmitter<any>();

  protected get label(): string | undefined {
    return this.options?.find(option => option.value === this.value)?.label;
  }

  protected onOpenPanel(event: MouseEvent): void {
    this.openPanel.emit(event);
  }

  protected onMove(direction: number): void {
    const index = this.options?.findIndex(option => option.value === this.value) || 0;
    let newIndex = index + direction;
    if (newIndex < 0) {
      newIndex = this.options ? this.options.length - 1 : 0;
    } else if (newIndex >= (this.options?.length || 0)) {
      newIndex = ((this.options?.length || 2) - 1) - 1;
    }
    this.value = this.options ? [ newIndex ] || null;
  }
}
