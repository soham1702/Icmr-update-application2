import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITransactions, Transactions } from '../transactions.model';
import { TransactionsService } from '../service/transactions.service';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { SupplierService } from 'app/entities/supplier/service/supplier.service';
import { IInventory } from 'app/entities/inventory/inventory.model';
import { InventoryService } from 'app/entities/inventory/service/inventory.service';
import { TransactionStatus } from 'app/entities/enumerations/transaction-status.model';

@Component({
  selector: 'jhi-transactions-update',
  templateUrl: './transactions-update.component.html',
})
export class TransactionsUpdateComponent implements OnInit {
  isSaving = false;
  transactionStatusValues = Object.keys(TransactionStatus);

  suppliersSharedCollection: ISupplier[] = [];
  inventoriesSharedCollection: IInventory[] = [];

  editForm = this.fb.group({
    id: [],
    stockReq: [null, [Validators.required]],
    stockProvided: [],
    status: [null, [Validators.required]],
    comment: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    supplier: [],
    inventory: [],
  });

  constructor(
    protected transactionsService: TransactionsService,
    protected supplierService: SupplierService,
    protected inventoryService: InventoryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactions }) => {
      if (transactions.id === undefined) {
        const today = dayjs().startOf('day');
        transactions.lastModified = today;
      }

      this.updateForm(transactions);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transactions = this.createFromForm();
    if (transactions.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionsService.update(transactions));
    } else {
      this.subscribeToSaveResponse(this.transactionsService.create(transactions));
    }
  }

  trackSupplierById(index: number, item: ISupplier): number {
    return item.id!;
  }

  trackInventoryById(index: number, item: IInventory): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactions>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(transactions: ITransactions): void {
    this.editForm.patchValue({
      id: transactions.id,
      stockReq: transactions.stockReq,
      stockProvided: transactions.stockProvided,
      status: transactions.status,
      comment: transactions.comment,
      lastModified: transactions.lastModified ? transactions.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: transactions.lastModifiedBy,
      supplier: transactions.supplier,
      inventory: transactions.inventory,
    });

    this.suppliersSharedCollection = this.supplierService.addSupplierToCollectionIfMissing(
      this.suppliersSharedCollection,
      transactions.supplier
    );
    this.inventoriesSharedCollection = this.inventoryService.addInventoryToCollectionIfMissing(
      this.inventoriesSharedCollection,
      transactions.inventory
    );
  }

  protected loadRelationshipsOptions(): void {
    this.supplierService
      .query()
      .pipe(map((res: HttpResponse<ISupplier[]>) => res.body ?? []))
      .pipe(
        map((suppliers: ISupplier[]) =>
          this.supplierService.addSupplierToCollectionIfMissing(suppliers, this.editForm.get('supplier')!.value)
        )
      )
      .subscribe((suppliers: ISupplier[]) => (this.suppliersSharedCollection = suppliers));

    this.inventoryService
      .query()
      .pipe(map((res: HttpResponse<IInventory[]>) => res.body ?? []))
      .pipe(
        map((inventories: IInventory[]) =>
          this.inventoryService.addInventoryToCollectionIfMissing(inventories, this.editForm.get('inventory')!.value)
        )
      )
      .subscribe((inventories: IInventory[]) => (this.inventoriesSharedCollection = inventories));
  }

  protected createFromForm(): ITransactions {
    return {
      ...new Transactions(),
      id: this.editForm.get(['id'])!.value,
      stockReq: this.editForm.get(['stockReq'])!.value,
      stockProvided: this.editForm.get(['stockProvided'])!.value,
      status: this.editForm.get(['status'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      supplier: this.editForm.get(['supplier'])!.value,
      inventory: this.editForm.get(['inventory'])!.value,
    };
  }
}
