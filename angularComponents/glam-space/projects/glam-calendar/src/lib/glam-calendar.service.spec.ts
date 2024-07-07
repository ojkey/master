import { TestBed } from '@angular/core/testing';

import { GlamCalendarService } from './glam-calendar.service';

describe('GlamCalendarService', () => {
  let service: GlamCalendarService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GlamCalendarService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
