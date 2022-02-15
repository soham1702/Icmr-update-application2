import dayjs from 'dayjs/esm';
import { IInventoryType } from 'app/entities/inventory-type/inventory-type.model';

export interface IInventoryMaster {
  id?: number;
  name?: string;
  description?: string | null;
  volume?: number | null;
  unit?: string;
  calulateVolume?: number | null;
  dimensions?: string | null;
  subTypeInd?: string | null;
  deleted?: boolean | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  inventoryType?: IInventoryType | null;
}

export class InventoryMaster implements IInventoryMaster {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string | null,
    public volume?: number | null,
    public unit?: string,
    public calulateVolume?: number | null,
    public dimensions?: string | null,
    public subTypeInd?: string | null,
    public deleted?: boolean | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public inventoryType?: IInventoryType | null
  ) {
    this.deleted = this.deleted ?? false;
  }
}

export function getInventoryMasterIdentifier(inventoryMaster: IInventoryMaster): number | undefined {
  return inventoryMaster.id;
}
