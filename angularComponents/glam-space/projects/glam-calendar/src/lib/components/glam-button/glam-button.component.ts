import { AfterViewInit, ChangeDetectorRef, Component, HostBinding, Input, OnInit } from '@angular/core';
import { GlamSize, GlamState } from '../model';

@Component({
  selector: 'glam-button',
  templateUrl: './glam-button.component.html',
  styleUrls: [ './glam-button.component.scss' ]
})
export class GlamButtonComponent implements OnInit {

  @Input()
  label?: string;

  @Input()
  color?: string;

  @Input()
  disabled?: boolean;

  @Input()
  set size(value: GlamSize) {
    this.currentSize = value;
    this.updateStyles();
  }

  @Input()
  set state(value: GlamState) {
    this.currentState = value;
    this.updateStyles();
  }

  @HostBinding('class')
  styleClasses = '';

  private currentSize?: GlamSize;
  private currentState?: GlamState;
  private focused = false;
  private hovered = false;
  private pressed = false;

  constructor(private cd: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    this.updateStyles();
  }

  private getStyleClasses(size?: GlamSize, state?: GlamState): string {
    return [
      size ? `size-${ size }` : 'size-regular',
      state ? `state-${ state }` : 'state-enabled',
    ].join(' ');
  }

  protected onMouseEnter(): void {
    this.hovered = true;
    this.updateStyles();
  }

  protected onMouseLeave(): void {
    this.hovered = false;
    if (this.pressed) {
      this.pressed = false;
    }
    if (this.focused) {
      this.focused = false;
    }
    this.updateStyles();
  }

  protected onMouseDown(): void {
    this.pressed = true;
    this.updateStyles();
  }

  protected onMouseUp(): void {
    this.pressed = false;
    this.updateStyles();
  }

  protected onFocusIn(): void {
    this.focused = true;
    this.updateStyles();
  }

  protected onFocusOut(): void {
    this.focused = false;
    this.updateStyles();
  }

  private updateStyles(): void {
    console.log('---> updateStyles');
    let state: GlamState = 'enabled';
    if (this.currentState) {
      state = this.currentState;
    } else {
      if (this.disabled) {
        state = 'disabled';
      } else {
        if (this.pressed) {
          state = 'pressed';
        } else if (this.focused) {
          state = 'focused';
        } else if (this.hovered) {
          state = 'hovered';
        }
      }
    }
    this.styleClasses = this.getStyleClasses(this.currentSize, state);
  }

}
