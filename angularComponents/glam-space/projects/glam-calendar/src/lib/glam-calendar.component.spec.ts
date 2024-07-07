import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GlamCalendarComponent } from './glam-calendar.component';

describe('GlamCalendarComponent', () => {
  let component: GlamCalendarComponent;
  let fixture: ComponentFixture<GlamCalendarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GlamCalendarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GlamCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
