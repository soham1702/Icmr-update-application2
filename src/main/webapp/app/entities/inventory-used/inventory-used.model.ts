import dayjs from 'dayjs/esm';
import { IInventory } from 'app/entities/inventory/inventory.model';

export interface IInventoryUsed {
  id?: number;
  stock?: number | null;
  capcity?: number | null;
  comment?: string | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  inventory?: IInventory | null;
}

export class InventoryUsed implements IInventoryUsed {
  constructor(
    public id?: number,
    public stock?: number | null,
    public capcity?: number | null,
    public comment?: string | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public inventory?: IInventory | null
  ) {}
}

export function getInventoryUsedIdentifier(inventoryUsed: IInventoryUsed): number | undefined {
  return inventoryUsed.id;
}
