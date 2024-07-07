import { startOfMonth } from 'date-fns/startOfMonth';
import { endOfMonth } from 'date-fns/endOfMonth';
import { addYears } from 'date-fns/addYears';
import { addMonths } from 'date-fns/addMonths';
import { addDays } from 'date-fns/addDays';
import { isNullOrUndefined } from './utils';

export enum DayOfWeek {
  Monday = 1,
  Tuesday = 2,
  Wednesday = 3,
  Thursday = 4,
  Friday = 5,
  Saturday = 6,
  Sunday = 0,
}

export interface Change {
  years?: number;
  months?: number;
  days?: number;
}


export class GlamDate {

  private constructor(public readonly date: Date) {
  }

  public static of(date: Date): GlamDate {
    return new GlamDate(date);
  }

  public static now(): GlamDate {
    return new GlamDate(new Date());
  }

  public getDayOfWeek(): number {
    return this.date.getDay();
  }

  public getDayOfMonth(): number {
    return this.date.getDate();
  }

  public startOfMonth(): GlamDate {
    return GlamDate.of(startOfMonth(this.date.getDay()));
  }

  public endOfMonth(): GlamDate {
    return GlamDate.of(endOfMonth(this.date.getDay()));
  }

  public plus(params: Change): GlamDate {
    let other = this.date;
    if (params.years) {
      other = addYears(other, params.years);
    }
    if (params.months) {
      other = addMonths(other, params.months);
    }
    if (params.days) {
      other = addDays(other, params.days);
    }
    return GlamDate.of(other);
  }

  public minus(params: Change): GlamDate {
    return this.plus({
      years: isNullOrUndefined(params.years) ? undefined : -params!.years,
      months: isNullOrUndefined(params.months) ? undefined : -params!.months,
      days: isNullOrUndefined(params.days) ? undefined : -params!.days,
    });
  }
}
