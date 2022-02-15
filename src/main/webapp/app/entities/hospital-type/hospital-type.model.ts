import dayjs from 'dayjs/esm';

export interface IHospitalType {
  id?: number;
  name?: string;
  desciption?: string | null;
  deleted?: boolean | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
}

export class HospitalType implements IHospitalType {
  constructor(
    public id?: number,
    public name?: string,
    public desciption?: string | null,
    public deleted?: boolean | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string
  ) {
    this.deleted = this.deleted ?? false;
  }
}

export function getHospitalTypeIdentifier(hospitalType: IHospitalType): number | undefined {
  return hospitalType.id;
}
