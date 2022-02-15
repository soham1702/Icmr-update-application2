import dayjs from 'dayjs/esm';
import { IState } from 'app/entities/state/state.model';
import { IDistrict } from 'app/entities/district/district.model';
import { ITaluka } from 'app/entities/taluka/taluka.model';
import { ICity } from 'app/entities/city/city.model';
import { IMunicipalCorp } from 'app/entities/municipal-corp/municipal-corp.model';
import { IHospitalType } from 'app/entities/hospital-type/hospital-type.model';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { HospitalCategory } from 'app/entities/enumerations/hospital-category.model';
import { HospitalSubCategory } from 'app/entities/enumerations/hospital-sub-category.model';
import { StatusInd } from 'app/entities/enumerations/status-ind.model';

export interface IHospital {
  id?: number;
  category?: HospitalCategory;
  subCategory?: HospitalSubCategory;
  contactNo?: string | null;
  latitude?: string | null;
  longitude?: string | null;
  docCount?: number | null;
  email?: string | null;
  name?: string;
  registrationNo?: string | null;
  address1?: string | null;
  address2?: string | null;
  area?: string | null;
  pinCode?: string;
  hospitalId?: number | null;
  odasFacilityId?: string | null;
  referenceNumber?: string | null;
  statusInd?: StatusInd | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  state?: IState | null;
  district?: IDistrict | null;
  taluka?: ITaluka | null;
  city?: ICity | null;
  municipalCorp?: IMunicipalCorp | null;
  hospitalType?: IHospitalType | null;
  suppliers?: ISupplier[] | null;
}

export class Hospital implements IHospital {
  constructor(
    public id?: number,
    public category?: HospitalCategory,
    public subCategory?: HospitalSubCategory,
    public contactNo?: string | null,
    public latitude?: string | null,
    public longitude?: string | null,
    public docCount?: number | null,
    public email?: string | null,
    public name?: string,
    public registrationNo?: string | null,
    public address1?: string | null,
    public address2?: string | null,
    public area?: string | null,
    public pinCode?: string,
    public hospitalId?: number | null,
    public odasFacilityId?: string | null,
    public referenceNumber?: string | null,
    public statusInd?: StatusInd | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public state?: IState | null,
    public district?: IDistrict | null,
    public taluka?: ITaluka | null,
    public city?: ICity | null,
    public municipalCorp?: IMunicipalCorp | null,
    public hospitalType?: IHospitalType | null,
    public suppliers?: ISupplier[] | null
  ) {}
}

export function getHospitalIdentifier(hospital: IHospital): number | undefined {
  return hospital.id;
}
