import dayjs from 'dayjs/esm';
import { IState } from 'app/entities/state/state.model';
import { IDistrict } from 'app/entities/district/district.model';
import { ITaluka } from 'app/entities/taluka/taluka.model';
import { ICity } from 'app/entities/city/city.model';
import { IInventoryType } from 'app/entities/inventory-type/inventory-type.model';
import { IHospital } from 'app/entities/hospital/hospital.model';
import { SupplierType } from 'app/entities/enumerations/supplier-type.model';
import { StatusInd } from 'app/entities/enumerations/status-ind.model';

export interface ISupplier {
  id?: number;
  name?: string;
  supplierType?: SupplierType;
  contactNo?: string | null;
  latitude?: string | null;
  longitude?: string | null;
  email?: string | null;
  registrationNo?: string | null;
  address1?: string | null;
  address2?: string | null;
  area?: string | null;
  pinCode?: string;
  statusInd?: StatusInd | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  state?: IState | null;
  district?: IDistrict | null;
  taluka?: ITaluka | null;
  city?: ICity | null;
  inventoryType?: IInventoryType | null;
  hospitals?: IHospital[] | null;
}

export class Supplier implements ISupplier {
  constructor(
    public id?: number,
    public name?: string,
    public supplierType?: SupplierType,
    public contactNo?: string | null,
    public latitude?: string | null,
    public longitude?: string | null,
    public email?: string | null,
    public registrationNo?: string | null,
    public address1?: string | null,
    public address2?: string | null,
    public area?: string | null,
    public pinCode?: string,
    public statusInd?: StatusInd | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public state?: IState | null,
    public district?: IDistrict | null,
    public taluka?: ITaluka | null,
    public city?: ICity | null,
    public inventoryType?: IInventoryType | null,
    public hospitals?: IHospital[] | null
  ) {}
}

export function getSupplierIdentifier(supplier: ISupplier): number | undefined {
  return supplier.id;
}
