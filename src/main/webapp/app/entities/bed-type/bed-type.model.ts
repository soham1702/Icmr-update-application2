import dayjs from 'dayjs/esm';

export interface IBedType {
  id?: number;
  name?: string;
  perDayOX?: number | null;
  deleted?: boolean | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
}

export class BedType implements IBedType {
  constructor(
    public id?: number,
    public name?: string,
    public perDayOX?: number | null,
    public deleted?: boolean | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string
  ) {
    this.deleted = this.deleted ?? false;
  }
}

export function getBedTypeIdentifier(bedType: IBedType): number | undefined {
  return bedType.id;
}
