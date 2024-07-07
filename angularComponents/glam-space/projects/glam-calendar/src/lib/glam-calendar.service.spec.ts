import { TestBed } from '@angular/core/testing';

import { getAdjustedStartOfWeek, GlamCalendarService } from './glam-calendar.service';
import { format } from 'date-fns/format';
import { parseISO } from 'date-fns/parseISO';

describe('GlamCalendarService', () => {
  let service: GlamCalendarService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GlamCalendarService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get first date', () => {
    const march = parseISO('2024-03-01');
    const result = getAdjustedStartOfWeek(march, 1);
    const actual = format(result, 'YYYY-MM-DD');
    expect(actual).toEqual('2024-02-26');
  });
});
