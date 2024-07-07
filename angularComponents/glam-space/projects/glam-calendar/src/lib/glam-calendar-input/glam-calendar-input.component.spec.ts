import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GlamCalendarInputComponent } from './glam-calendar-input.component';

describe('GlamCalendarComponent', () => {
  let component: GlamCalendarInputComponent;
  let fixture: ComponentFixture<GlamCalendarInputComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GlamCalendarInputComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GlamCalendarInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
