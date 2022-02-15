import dayjs from 'dayjs/esm';
import { IInventoryMaster } from 'app/entities/inventory-master/inventory-master.model';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { IHospital } from 'app/entities/hospital/hospital.model';

export interface IInventory {
  id?: number;
  stock?: number;
  capcity?: number | null;
  installedCapcity?: number | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  inventoryMaster?: IInventoryMaster | null;
  supplier?: ISupplier | null;
  hospital?: IHospital | null;
}

export class Inventory implements IInventory {
  constructor(
    public id?: number,
    public stock?: number,
    public capcity?: number | null,
    public installedCapcity?: number | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public inventoryMaster?: IInventoryMaster | null,
    public supplier?: ISupplier | null,
    public hospital?: IHospital | null
  ) {}
}

export function getInventoryIdentifier(inventory: IInventory): number | undefined {
  return inventory.id;
}
