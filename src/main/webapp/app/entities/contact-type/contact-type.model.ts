import dayjs from 'dayjs/esm';

export interface IContactType {
  id?: number;
  name?: string;
  deleted?: boolean | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
}

export class ContactType implements IContactType {
  constructor(
    public id?: number,
    public name?: string,
    public deleted?: boolean | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string
  ) {
    this.deleted = this.deleted ?? false;
  }
}

export function getContactTypeIdentifier(contactType: IContactType): number | undefined {
  return contactType.id;
}
