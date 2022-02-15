import dayjs from 'dayjs/esm';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { IHospital } from 'app/entities/hospital/hospital.model';
import { ITransactions } from 'app/entities/transactions/transactions.model';
import { ITrip } from 'app/entities/trip/trip.model';
import { TransactionStatus } from 'app/entities/enumerations/transaction-status.model';

export interface ITripDetails {
  id?: number;
  stockSent?: number;
  stockRec?: number | null;
  comment?: string | null;
  receiverComment?: string | null;
  status?: TransactionStatus;
  createdDate?: dayjs.Dayjs;
  createdBy?: string;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  supplier?: ISupplier | null;
  hospital?: IHospital | null;
  transactions?: ITransactions | null;
  trip?: ITrip | null;
}

export class TripDetails implements ITripDetails {
  constructor(
    public id?: number,
    public stockSent?: number,
    public stockRec?: number | null,
    public comment?: string | null,
    public receiverComment?: string | null,
    public status?: TransactionStatus,
    public createdDate?: dayjs.Dayjs,
    public createdBy?: string,
    public lastModified?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public supplier?: ISupplier | null,
    public hospital?: IHospital | null,
    public transactions?: ITransactions | null,
    public trip?: ITrip | null
  ) {}
}

export function getTripDetailsIdentifier(tripDetails: ITripDetails): number | undefined {
  return tripDetails.id;
}
