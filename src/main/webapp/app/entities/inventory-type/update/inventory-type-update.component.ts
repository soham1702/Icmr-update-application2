import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IInventoryType, InventoryType } from '../inventory-type.model';
import { InventoryTypeService } from '../service/inventory-type.service';

@Component({
  selector: 'jhi-inventory-type-update',
  templateUrl: './inventory-type-update.component.html',
})
export class InventoryTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    deleted: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(protected inventoryTypeService: InventoryTypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryType }) => {
      if (inventoryType.id === undefined) {
        const today = dayjs().startOf('day');
        inventoryType.lastModified = today;
      }

      this.updateForm(inventoryType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventoryType = this.createFromForm();
    if (inventoryType.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryTypeService.update(inventoryType));
    } else {
      this.subscribeToSaveResponse(this.inventoryTypeService.create(inventoryType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventoryType>>): void {
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

  protected updateForm(inventoryType: IInventoryType): void {
    this.editForm.patchValue({
      id: inventoryType.id,
      name: inventoryType.name,
      deleted: inventoryType.deleted,
      lastModified: inventoryType.lastModified ? inventoryType.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: inventoryType.lastModifiedBy,
    });
  }

  protected createFromForm(): IInventoryType {
    return {
      ...new InventoryType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }
}
