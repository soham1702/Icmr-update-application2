import dayjs from 'dayjs/esm';
import { IBedType } from 'app/entities/bed-type/bed-type.model';
import { IHospital } from 'app/entities/hospital/hospital.model';

export interface IBedTransactions {
  id?: number;
  occupied?: number;
  onCylinder?: number | null;
  onLMO?: number | null;
  onConcentrators?: number | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  bedType?: IBedType | null;
  hospital?: IHospital | null;
}

export class BedTransactions implements IBedTransactions {
  constructor(
    public id?: number,
    public occupied?: number,
    public onCylinder?: number | null,
    public onLMO?: number | null,
    public onConcentrators?: number | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public bedType?: IBedType | null,
    public hospital?: IHospital | null
  ) {}
}

export function getBedTransactionsIdentifier(bedTransactions: IBedTransactions): number | undefined {
  return bedTransactions.id;
}
