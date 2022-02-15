import dayjs from 'dayjs/esm';

export interface IInventoryType {
  id?: number;
  name?: string;
  deleted?: boolean | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
}

export class InventoryType implements IInventoryType {
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

export function getInventoryTypeIdentifier(inventoryType: IInventoryType): number | undefined {
  return inventoryType.id;
}
