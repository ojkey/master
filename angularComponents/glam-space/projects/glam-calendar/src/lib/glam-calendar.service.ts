import { Injectable } from '@angular/core';
import { DayOption, Option } from './model/option';
import { DayOfWeek } from './model/date';
import { startOfMonth } from 'date-fns/startOfMonth';
import { endOfMonth } from 'date-fns/endOfMonth';
import { eachDayOfInterval } from 'date-fns/eachDayOfInterval';
import { addDays } from 'date-fns/addDays';
import { isDateEquals } from './model/utils';
import { parseISO } from 'date-fns/parseISO';

@Injectable({
  providedIn: 'root'
})
export class GlamCalendarService {

  public years: Option[] = [
    {
      label: '2024',
      value: 2024,
    }, {
      label: '2023',
      value: 2023,
    }
  ];

  public months: Option[] = [
    {
      label: 'January',
      value: 1,
    },
    {
      label: 'February',
      value: 2,
    },
    {
      label: 'March',
      value: 3,
    },
    {
      label: 'April',
      value: 4,
    },
    {
      label: 'May',
      value: 5,
    },
    {
      label: 'June',
      value: 6,
    },
    {
      label: 'July',
      value: 7,
    },
    {
      label: 'August',
      value: 8,
    },
    {
      label: 'September',
      value: 9,
    },
    {
      label: 'October',
      value: 10,
    },
    {
      label: 'November',
      value: 11,
    },
    {
      label: 'December',
      value: 12,
    },
  ];

  public weekDays: Option[] = [
    {
      label: 'M',
      value: DayOfWeek.Monday,
    },
    {
      label: 'T',
      value: DayOfWeek.Tuesday,
    },
    {
      label: 'W',
      value: DayOfWeek.Wednesday,
    },
    {
      label: 'T',
      value: DayOfWeek.Thursday,
    },
    {
      label: 'F',
      value: DayOfWeek.Friday,
    },
    {
      label: 'S',
      value: DayOfWeek.Saturday,
    },
    {
      label: 'S',
      value: DayOfWeek.Sunday,
    },
  ];

  public days = createDayOptions(2024, 6, this.weekDays[0].value);

  public selected = parseISO('2024-07-11');
}


// function createDayOptions(weekDays: number[]): DayOption[] {
//   const now = GlamDate.now();
//   const start =  now.startOfMonth();
//   const end = now.endOfMonth();
//   const max = end.getDayOfMonth();
// }


/**
 * Generate days for a calendar component with 6 weeks of days, including previous and next months' days
 * @param year - The year for the calendar view
 * @param month - The month for the calendar view (0-based index, January is 0)
 * @param firstDayOfWeek - The desired first day of the week (0 for Sunday, 1 for Monday, etc.)
 * @returns {CalendarDay[]} - An array of CalendarDay objects representing each day in the 6-week calendar view
 */
function createDayOptions(year: number, month: number, firstDayOfWeek: number = 0): DayOption[] {
  // Start and end of the current month
  const startOfCurrentMonth = startOfMonth(new Date(year, month));
  const endOfCurrentMonth = endOfMonth(startOfCurrentMonth);

  // Start and end of the calendar view (always 6 full weeks)
  const startOfCalendar = getAdjustedStartOfWeek(startOfCurrentMonth, firstDayOfWeek);
  const endOfCalendar = getAdjustedEndOfWeek(addDays(startOfCalendar, 6 * 7 - 1), firstDayOfWeek);

  // Generate days for the calendar view
  const days = eachDayOfInterval({
    start: startOfCalendar,
    end: endOfCalendar
  });

  // Determine if a given date is today
  const today = new Date();

  // Map each day to a CalendarDay object
  return days.map(date => ({
    value: date,
    currentMonth: date.getMonth() === month,
    today: isDateEquals(date, today),
    label: String(date.getDate())
  }));
}

/**
 * Adjust the start of the week to the specified first day.
 * @param date - The date to adjust
 * @param firstDayOfWeek - The desired first day of the week (0 for Sunday, 1 for Monday, etc.)
 * @returns {Date} - The adjusted start of the week
 */
export function getAdjustedStartOfWeek(date: Date, firstDayOfWeek: number): Date {
  const currentDayOfWeek = date.getDay();
  if (currentDayOfWeek === firstDayOfWeek) {
    return addDays(date, -7);
  }
  if (currentDayOfWeek < firstDayOfWeek) {
    return addDays(date, -(7 - (firstDayOfWeek - currentDayOfWeek)));
  }
  return addDays(date, -(currentDayOfWeek - firstDayOfWeek));
}

/**
 * Adjust the end of the week to the specified last day.
 * @param date - The date to adjust
 * @param firstDayOfWeek - The desired first day of the week (0 for Sunday, 1 for Monday, etc.)
 * @returns {Date} - The adjusted end of the week
 */
export function getAdjustedEndOfWeek(date: Date, firstDayOfWeek: number): Date {
  const startOfNextWeek = addDays(getAdjustedStartOfWeek(date, firstDayOfWeek), 7);
  return addDays(startOfNextWeek, -1);
}
