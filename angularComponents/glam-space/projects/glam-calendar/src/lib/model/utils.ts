export function isNullOrUndefined<T>(value: T | null | undefined): value is null | undefined {
  return value === null || value === undefined;
}

export function isDateEquals(date1?: Date, date2?: Date): boolean {
  return date1?.getFullYear() === date2?.getFullYear() &&
    date1?.getMonth() === date2?.getMonth() &&
    date1?.getDate() === date2?.getDate();
}
