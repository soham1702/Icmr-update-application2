import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IInventory, Inventory } from '../inventory.model';
import { InventoryService } from '../service/inventory.service';
import { IInventoryMaster } from 'app/entities/inventory-master/inventory-master.model';
import { InventoryMasterService } from 'app/entities/inventory-master/service/inventory-master.service';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { SupplierService } from 'app/entities/supplier/service/supplier.service';
import { IHospital } from 'app/entities/hospital/hospital.model';
import { HospitalService } from 'app/entities/hospital/service/hospital.service';

@Component({
  selector: 'jhi-inventory-update',
  templateUrl: './inventory-update.component.html',
})
export class InventoryUpdateComponent implements OnInit {
  isSaving = false;

  inventoryMastersSharedCollection: IInventoryMaster[] = [];
  suppliersSharedCollection: ISupplier[] = [];
  hospitalsSharedCollection: IHospital[] = [];

  editForm = this.fb.group({
    id: [],
    stock: [null, [Validators.required]],
    capcity: [],
    installedCapcity: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    inventoryMaster: [],
    supplier: [],
    hospital: [],
  });

  constructor(
    protected inventoryService: InventoryService,
    protected inventoryMasterService: InventoryMasterService,
    protected supplierService: SupplierService,
    protected hospitalService: HospitalService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventory }) => {
      if (inventory.id === undefined) {
        const today = dayjs().startOf('day');
        inventory.lastModified = today;
      }

      this.updateForm(inventory);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventory = this.createFromForm();
    if (inventory.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryService.update(inventory));
    } else {
      this.subscribeToSaveResponse(this.inventoryService.create(inventory));
    }
  }

  trackInventoryMasterById(index: number, item: IInventoryMaster): number {
    return item.id!;
  }

  trackSupplierById(index: number, item: ISupplier): number {
    return item.id!;
  }

  trackHospitalById(index: number, item: IHospital): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventory>>): void {
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

  protected updateForm(inventory: IInventory): void {
    this.editForm.patchValue({
      id: inventory.id,
      stock: inventory.stock,
      capcity: inventory.capcity,
      installedCapcity: inventory.installedCapcity,
      lastModified: inventory.lastModified ? inventory.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: inventory.lastModifiedBy,
      inventoryMaster: inventory.inventoryMaster,
      supplier: inventory.supplier,
      hospital: inventory.hospital,
    });

    this.inventoryMastersSharedCollection = this.inventoryMasterService.addInventoryMasterToCollectionIfMissing(
      this.inventoryMastersSharedCollection,
      inventory.inventoryMaster
    );
    this.suppliersSharedCollection = this.supplierService.addSupplierToCollectionIfMissing(
      this.suppliersSharedCollection,
      inventory.supplier
    );
    this.hospitalsSharedCollection = this.hospitalService.addHospitalToCollectionIfMissing(
      this.hospitalsSharedCollection,
      inventory.hospital
    );
  }

  protected loadRelationshipsOptions(): void {
    this.inventoryMasterService
      .query()
      .pipe(map((res: HttpResponse<IInventoryMaster[]>) => res.body ?? []))
      .pipe(
        map((inventoryMasters: IInventoryMaster[]) =>
          this.inventoryMasterService.addInventoryMasterToCollectionIfMissing(inventoryMasters, this.editForm.get('inventoryMaster')!.value)
        )
      )
      .subscribe((inventoryMasters: IInventoryMaster[]) => (this.inventoryMastersSharedCollection = inventoryMasters));

    this.supplierService
      .query()
      .pipe(map((res: HttpResponse<ISupplier[]>) => res.body ?? []))
      .pipe(
        map((suppliers: ISupplier[]) =>
          this.supplierService.addSupplierToCollectionIfMissing(suppliers, this.editForm.get('supplier')!.value)
        )
      )
      .subscribe((suppliers: ISupplier[]) => (this.suppliersSharedCollection = suppliers));

    this.hospitalService
      .query()
      .pipe(map((res: HttpResponse<IHospital[]>) => res.body ?? []))
      .pipe(
        map((hospitals: IHospital[]) =>
          this.hospitalService.addHospitalToCollectionIfMissing(hospitals, this.editForm.get('hospital')!.value)
        )
      )
      .subscribe((hospitals: IHospital[]) => (this.hospitalsSharedCollection = hospitals));
  }

  protected createFromForm(): IInventory {
    return {
      ...new Inventory(),
      id: this.editForm.get(['id'])!.value,
      stock: this.editForm.get(['stock'])!.value,
      capcity: this.editForm.get(['capcity'])!.value,
      installedCapcity: this.editForm.get(['installedCapcity'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      inventoryMaster: this.editForm.get(['inventoryMaster'])!.value,
      supplier: this.editForm.get(['supplier'])!.value,
      hospital: this.editForm.get(['hospital'])!.value,
    };
  }
}
