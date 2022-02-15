import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IInventoryUsed, InventoryUsed } from '../inventory-used.model';
import { InventoryUsedService } from '../service/inventory-used.service';
import { IInventory } from 'app/entities/inventory/inventory.model';
import { InventoryService } from 'app/entities/inventory/service/inventory.service';

@Component({
  selector: 'jhi-inventory-used-update',
  templateUrl: './inventory-used-update.component.html',
})
export class InventoryUsedUpdateComponent implements OnInit {
  isSaving = false;

  inventoriesSharedCollection: IInventory[] = [];

  editForm = this.fb.group({
    id: [],
    stock: [],
    capcity: [],
    comment: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    inventory: [],
  });

  constructor(
    protected inventoryUsedService: InventoryUsedService,
    protected inventoryService: InventoryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryUsed }) => {
      if (inventoryUsed.id === undefined) {
        const today = dayjs().startOf('day');
        inventoryUsed.lastModified = today;
      }

      this.updateForm(inventoryUsed);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventoryUsed = this.createFromForm();
    if (inventoryUsed.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryUsedService.update(inventoryUsed));
    } else {
      this.subscribeToSaveResponse(this.inventoryUsedService.create(inventoryUsed));
    }
  }

  trackInventoryById(index: number, item: IInventory): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventoryUsed>>): void {
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

  protected updateForm(inventoryUsed: IInventoryUsed): void {
    this.editForm.patchValue({
      id: inventoryUsed.id,
      stock: inventoryUsed.stock,
      capcity: inventoryUsed.capcity,
      comment: inventoryUsed.comment,
      lastModified: inventoryUsed.lastModified ? inventoryUsed.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: inventoryUsed.lastModifiedBy,
      inventory: inventoryUsed.inventory,
    });

    this.inventoriesSharedCollection = this.inventoryService.addInventoryToCollectionIfMissing(
      this.inventoriesSharedCollection,
      inventoryUsed.inventory
    );
  }

  protected loadRelationshipsOptions(): void {
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

  protected createFromForm(): IInventoryUsed {
    return {
      ...new InventoryUsed(),
      id: this.editForm.get(['id'])!.value,
      stock: this.editForm.get(['stock'])!.value,
      capcity: this.editForm.get(['capcity'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      inventory: this.editForm.get(['inventory'])!.value,
    };
  }
}
