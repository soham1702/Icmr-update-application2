import dayjs from 'dayjs/esm';
import { IAuditType } from 'app/entities/audit-type/audit-type.model';
import { IHospital } from 'app/entities/hospital/hospital.model';
import { ISupplier } from 'app/entities/supplier/supplier.model';

export interface IAuditSystem {
  id?: number;
  auditorName?: string;
  defectCount?: number | null;
  defectFixCount?: number | null;
  inspectionDate?: dayjs.Dayjs;
  remark?: string | null;
  status?: string | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  auditType?: IAuditType | null;
  hospital?: IHospital | null;
  supplier?: ISupplier | null;
}

export class AuditSystem implements IAuditSystem {
  constructor(
    public id?: number,
    public auditorName?: string,
    public defectCount?: number | null,
    public defectFixCount?: number | null,
    public inspectionDate?: dayjs.Dayjs,
    public remark?: string | null,
    public status?: string | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public auditType?: IAuditType | null,
    public hospital?: IHospital | null,
    public supplier?: ISupplier | null
  ) {}
}

export function getAuditSystemIdentifier(auditSystem: IAuditSystem): number | undefined {
  return auditSystem.id;
}
