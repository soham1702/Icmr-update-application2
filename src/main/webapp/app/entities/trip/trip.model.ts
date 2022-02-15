import dayjs from 'dayjs/esm';
import { ITripDetails } from 'app/entities/trip-details/trip-details.model';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { TransactionStatus } from 'app/entities/enumerations/transaction-status.model';

export interface ITrip {
  id?: number;
  trackingNo?: string;
  mobaId?: number;
  numberPlate?: string;
  stock?: number;
  status?: TransactionStatus;
  createdDate?: dayjs.Dayjs;
  createdBy?: string;
  lastModified?: dayjs.Dayjs | null;
  comment?: string | null;
  lastModifiedBy?: string | null;
  tripDetails?: ITripDetails[] | null;
  supplier?: ISupplier | null;
}

export class Trip implements ITrip {
  constructor(
    public id?: number,
    public trackingNo?: string,
    public mobaId?: number,
    public numberPlate?: string,
    public stock?: number,
    public status?: TransactionStatus,
    public createdDate?: dayjs.Dayjs,
    public createdBy?: string,
    public lastModified?: dayjs.Dayjs | null,
    public comment?: string | null,
    public lastModifiedBy?: string | null,
    public tripDetails?: ITripDetails[] | null,
    public supplier?: ISupplier | null
  ) {}
}

export function getTripIdentifier(trip: ITrip): number | undefined {
  return trip.id;
}
