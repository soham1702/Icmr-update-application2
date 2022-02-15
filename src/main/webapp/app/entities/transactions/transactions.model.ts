import dayjs from 'dayjs/esm';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { IInventory } from 'app/entities/inventory/inventory.model';
import { TransactionStatus } from 'app/entities/enumerations/transaction-status.model';

export interface ITransactions {
  id?: number;
  stockReq?: number;
  stockProvided?: number | null;
  status?: TransactionStatus;
  comment?: string | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  supplier?: ISupplier | null;
  inventory?: IInventory | null;
}

export class Transactions implements ITransactions {
  constructor(
    public id?: number,
    public stockReq?: number,
    public stockProvided?: number | null,
    public status?: TransactionStatus,
    public comment?: string | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public supplier?: ISupplier | null,
    public inventory?: IInventory | null
  ) {}
}

export function getTransactionsIdentifier(transactions: ITransactions): number | undefined {
  return transactions.id;
}
