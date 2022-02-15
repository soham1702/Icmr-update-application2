import dayjs from 'dayjs/esm';

export interface IDivision {
  id?: number;
  name?: string;
  deleted?: boolean | null;
  lgdCode?: number | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
}

export class Division implements IDivision {
  constructor(
    public id?: number,
    public name?: string,
    public deleted?: boolean | null,
    public lgdCode?: number | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string
  ) {
    this.deleted = this.deleted ?? false;
  }
}

export function getDivisionIdentifier(division: IDivision): number | undefined {
  return division.id;
}
