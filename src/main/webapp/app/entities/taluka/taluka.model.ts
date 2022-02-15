import dayjs from 'dayjs/esm';
import { IDistrict } from 'app/entities/district/district.model';

export interface ITaluka {
  id?: number;
  name?: string;
  deleted?: boolean | null;
  lgdCode?: number | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  district?: IDistrict | null;
}

export class Taluka implements ITaluka {
  constructor(
    public id?: number,
    public name?: string,
    public deleted?: boolean | null,
    public lgdCode?: number | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public district?: IDistrict | null
  ) {
    this.deleted = this.deleted ?? false;
  }
}

export function getTalukaIdentifier(taluka: ITaluka): number | undefined {
  return taluka.id;
}
