import dayjs from 'dayjs/esm';

export interface IAuditType {
  id?: number;
  name?: string;
  deleted?: boolean | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
}

export class AuditType implements IAuditType {
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

export function getAuditTypeIdentifier(auditType: IAuditType): number | undefined {
  return auditType.id;
}
