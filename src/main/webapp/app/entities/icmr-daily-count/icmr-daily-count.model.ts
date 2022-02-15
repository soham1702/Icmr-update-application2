import dayjs from 'dayjs/esm';

export interface IICMRDailyCount {
  id?: number;
  totalSamplesTested?: number | null;
  totalNegative?: number | null;
  totalPositive?: number | null;
  currentPreviousMonthTest?: number | null;
  districtId?: number | null;
  remarks?: string | null;
  editedOn?: dayjs.Dayjs | null;
  updatedDate?: dayjs.Dayjs | null;
  freeField1?: string | null;
  freeField2?: string | null;
}

export class ICMRDailyCount implements IICMRDailyCount {
  constructor(
    public id?: number,
    public totalSamplesTested?: number | null,
    public totalNegative?: number | null,
    public totalPositive?: number | null,
    public currentPreviousMonthTest?: number | null,
    public districtId?: number | null,
    public remarks?: string | null,
    public editedOn?: dayjs.Dayjs | null,
    public updatedDate?: dayjs.Dayjs | null,
    public freeField1?: string | null,
    public freeField2?: string | null
  ) {}
}

export function getICMRDailyCountIdentifier(iCMRDailyCount: IICMRDailyCount): number | undefined {
  return iCMRDailyCount.id;
}
