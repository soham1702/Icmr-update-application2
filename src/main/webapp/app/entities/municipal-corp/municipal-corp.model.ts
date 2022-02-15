import dayjs from 'dayjs/esm';
import { IDistrict } from 'app/entities/district/district.model';

export interface IMunicipalCorp {
  id?: number;
  name?: string;
  deleted?: boolean | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  district?: IDistrict | null;
}

export class MunicipalCorp implements IMunicipalCorp {
  constructor(
    public id?: number,
    public name?: string,
    public deleted?: boolean | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public district?: IDistrict | null
  ) {
    this.deleted = this.deleted ?? false;
  }
}

export function getMunicipalCorpIdentifier(municipalCorp: IMunicipalCorp): number | undefined {
  return municipalCorp.id;
}
