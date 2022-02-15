import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IInventoryMaster, InventoryMaster } from '../inventory-master.model';
import { InventoryMasterService } from '../service/inventory-master.service';
import { IInventoryType } from 'app/entities/inventory-type/inventory-type.model';
import { InventoryTypeService } from 'app/entities/inventory-type/service/inventory-type.service';

@Component({
  selector: 'jhi-inventory-master-update',
  templateUrl: './inventory-master-update.component.html',
})
export class InventoryMasterUpdateComponent implements OnInit {
  isSaving = false;

  inventoryTypesSharedCollection: IInventoryType[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    volume: [],
    unit: [null, [Validators.required]],
    calulateVolume: [],
    dimensions: [],
    subTypeInd: [],
    deleted: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    inventoryType: [],
  });

  constructor(
    protected inventoryMasterService: InventoryMasterService,
    protected inventoryTypeService: InventoryTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryMaster }) => {
      if (inventoryMaster.id === undefined) {
        const today = dayjs().startOf('day');
        inventoryMaster.lastModified = today;
      }

      this.updateForm(inventoryMaster);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventoryMaster = this.createFromForm();
    if (inventoryMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryMasterService.update(inventoryMaster));
    } else {
      this.subscribeToSaveResponse(this.inventoryMasterService.create(inventoryMaster));
    }
  }

  trackInventoryTypeById(index: number, item: IInventoryType): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventoryMaster>>): void {
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

  protected updateForm(inventoryMaster: IInventoryMaster): void {
    this.editForm.patchValue({
      id: inventoryMaster.id,
      name: inventoryMaster.name,
      description: inventoryMaster.description,
      volume: inventoryMaster.volume,
      unit: inventoryMaster.unit,
      calulateVolume: inventoryMaster.calulateVolume,
      dimensions: inventoryMaster.dimensions,
      subTypeInd: inventoryMaster.subTypeInd,
      deleted: inventoryMaster.deleted,
      lastModified: inventoryMaster.lastModified ? inventoryMaster.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: inventoryMaster.lastModifiedBy,
      inventoryType: inventoryMaster.inventoryType,
    });

    this.inventoryTypesSharedCollection = this.inventoryTypeService.addInventoryTypeToCollectionIfMissing(
      this.inventoryTypesSharedCollection,
      inventoryMaster.inventoryType
    );
  }

  protected loadRelationshipsOptions(): void {
    this.inventoryTypeService
      .query()
      .pipe(map((res: HttpResponse<IInventoryType[]>) => res.body ?? []))
      .pipe(
        map((inventoryTypes: IInventoryType[]) =>
          this.inventoryTypeService.addInventoryTypeToCollectionIfMissing(inventoryTypes, this.editForm.get('inventoryType')!.value)
        )
      )
      .subscribe((inventoryTypes: IInventoryType[]) => (this.inventoryTypesSharedCollection = inventoryTypes));
  }

  protected createFromForm(): IInventoryMaster {
    return {
      ...new InventoryMaster(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      volume: this.editForm.get(['volume'])!.value,
      unit: this.editForm.get(['unit'])!.value,
      calulateVolume: this.editForm.get(['calulateVolume'])!.value,
      dimensions: this.editForm.get(['dimensions'])!.value,
      subTypeInd: this.editForm.get(['subTypeInd'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      inventoryType: this.editForm.get(['inventoryType'])!.value,
    };
  }
}
