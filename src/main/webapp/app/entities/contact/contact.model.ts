import dayjs from 'dayjs/esm';
import { IContactType } from 'app/entities/contact-type/contact-type.model';
import { IHospital } from 'app/entities/hospital/hospital.model';
import { ISupplier } from 'app/entities/supplier/supplier.model';

export interface IContact {
  id?: number;
  name?: string;
  contactNo?: string | null;
  email?: string | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  contactType?: IContactType | null;
  hospital?: IHospital | null;
  supplier?: ISupplier | null;
}

export class Contact implements IContact {
  constructor(
    public id?: number,
    public name?: string,
    public contactNo?: string | null,
    public email?: string | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public contactType?: IContactType | null,
    public hospital?: IHospital | null,
    public supplier?: ISupplier | null
  ) {}
}

export function getContactIdentifier(contact: IContact): number | undefined {
  return contact.id;
}
