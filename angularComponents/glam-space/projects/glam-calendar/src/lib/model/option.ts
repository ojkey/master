export interface Tuple<V = any> {
  [field: string | symbol]: V;
}

export interface Option<T = any> {
  label: string;
  value: T;
}

export interface DayOption extends Option<Date> {
  currentMonth: boolean;
  today: boolean;
}
