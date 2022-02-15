import dayjs from 'dayjs/esm';
import { ITaluka } from 'app/entities/taluka/taluka.model';

export interface ICity {
  id?: number;
  name?: string;
  deleted?: boolean | null;
  lgdCode?: number | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  taluka?: ITaluka | null;
}

export class City implements ICity {
  constructor(
    public id?: number,
    public name?: string,
    public deleted?: boolean | null,
    public lgdCode?: number | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public taluka?: ITaluka | null
  ) {
    this.deleted = this.deleted ?? false;
  }
}

export function getCityIdentifier(city: ICity): number | undefined {
  return city.id;
}
