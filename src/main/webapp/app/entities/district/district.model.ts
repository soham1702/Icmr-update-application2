import dayjs from 'dayjs/esm';
import { IState } from 'app/entities/state/state.model';
import { IDivision } from 'app/entities/division/division.model';

export interface IDistrict {
  id?: number;
  name?: string;
  deleted?: boolean | null;
  lgdCode?: number | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  state?: IState | null;
  division?: IDivision | null;
}

export class District implements IDistrict {
  constructor(
    public id?: number,
    public name?: string,
    public deleted?: boolean | null,
    public lgdCode?: number | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public state?: IState | null,
    public division?: IDivision | null
  ) {
    this.deleted = this.deleted ?? false;
  }
}

export function getDistrictIdentifier(district: IDistrict): number | undefined {
  return district.id;
}
