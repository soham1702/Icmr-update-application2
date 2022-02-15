import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISupplier, Supplier } from '../supplier.model';
import { SupplierService } from '../service/supplier.service';
import { IState } from 'app/entities/state/state.model';
import { StateService } from 'app/entities/state/service/state.service';
import { IDistrict } from 'app/entities/district/district.model';
import { DistrictService } from 'app/entities/district/service/district.service';
import { ITaluka } from 'app/entities/taluka/taluka.model';
import { TalukaService } from 'app/entities/taluka/service/taluka.service';
import { ICity } from 'app/entities/city/city.model';
import { CityService } from 'app/entities/city/service/city.service';
import { IInventoryType } from 'app/entities/inventory-type/inventory-type.model';
import { InventoryTypeService } from 'app/entities/inventory-type/service/inventory-type.service';
import { SupplierType } from 'app/entities/enumerations/supplier-type.model';
import { StatusInd } from 'app/entities/enumerations/status-ind.model';

@Component({
  selector: 'jhi-supplier-update',
  templateUrl: './supplier-update.component.html',
})
export class SupplierUpdateComponent implements OnInit {
  isSaving = false;
  supplierTypeValues = Object.keys(SupplierType);
  statusIndValues = Object.keys(StatusInd);

  statesSharedCollection: IState[] = [];
  districtsSharedCollection: IDistrict[] = [];
  talukasSharedCollection: ITaluka[] = [];
  citiesSharedCollection: ICity[] = [];
  inventoryTypesSharedCollection: IInventoryType[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    supplierType: [null, [Validators.required]],
    contactNo: [],
    latitude: [],
    longitude: [],
    email: [],
    registrationNo: [],
    address1: [],
    address2: [],
    area: [],
    pinCode: [null, [Validators.required]],
    statusInd: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    state: [],
    district: [],
    taluka: [],
    city: [],
    inventoryType: [],
  });

  constructor(
    protected supplierService: SupplierService,
    protected stateService: StateService,
    protected districtService: DistrictService,
    protected talukaService: TalukaService,
    protected cityService: CityService,
    protected inventoryTypeService: InventoryTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supplier }) => {
      if (supplier.id === undefined) {
        const today = dayjs().startOf('day');
        supplier.lastModified = today;
      }

      this.updateForm(supplier);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const supplier = this.createFromForm();
    if (supplier.id !== undefined) {
      this.subscribeToSaveResponse(this.supplierService.update(supplier));
    } else {
      this.subscribeToSaveResponse(this.supplierService.create(supplier));
    }
  }

  trackStateById(index: number, item: IState): number {
    return item.id!;
  }

  trackDistrictById(index: number, item: IDistrict): number {
    return item.id!;
  }

  trackTalukaById(index: number, item: ITaluka): number {
    return item.id!;
  }

  trackCityById(index: number, item: ICity): number {
    return item.id!;
  }

  trackInventoryTypeById(index: number, item: IInventoryType): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplier>>): void {
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

  protected updateForm(supplier: ISupplier): void {
    this.editForm.patchValue({
      id: supplier.id,
      name: supplier.name,
      supplierType: supplier.supplierType,
      contactNo: supplier.contactNo,
      latitude: supplier.latitude,
      longitude: supplier.longitude,
      email: supplier.email,
      registrationNo: supplier.registrationNo,
      address1: supplier.address1,
      address2: supplier.address2,
      area: supplier.area,
      pinCode: supplier.pinCode,
      statusInd: supplier.statusInd,
      lastModified: supplier.lastModified ? supplier.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: supplier.lastModifiedBy,
      state: supplier.state,
      district: supplier.district,
      taluka: supplier.taluka,
      city: supplier.city,
      inventoryType: supplier.inventoryType,
    });

    this.statesSharedCollection = this.stateService.addStateToCollectionIfMissing(this.statesSharedCollection, supplier.state);
    this.districtsSharedCollection = this.districtService.addDistrictToCollectionIfMissing(
      this.districtsSharedCollection,
      supplier.district
    );
    this.talukasSharedCollection = this.talukaService.addTalukaToCollectionIfMissing(this.talukasSharedCollection, supplier.taluka);
    this.citiesSharedCollection = this.cityService.addCityToCollectionIfMissing(this.citiesSharedCollection, supplier.city);
    this.inventoryTypesSharedCollection = this.inventoryTypeService.addInventoryTypeToCollectionIfMissing(
      this.inventoryTypesSharedCollection,
      supplier.inventoryType
    );
  }

  protected loadRelationshipsOptions(): void {
    this.stateService
      .query()
      .pipe(map((res: HttpResponse<IState[]>) => res.body ?? []))
      .pipe(map((states: IState[]) => this.stateService.addStateToCollectionIfMissing(states, this.editForm.get('state')!.value)))
      .subscribe((states: IState[]) => (this.statesSharedCollection = states));

    this.districtService
      .query()
      .pipe(map((res: HttpResponse<IDistrict[]>) => res.body ?? []))
      .pipe(
        map((districts: IDistrict[]) =>
          this.districtService.addDistrictToCollectionIfMissing(districts, this.editForm.get('district')!.value)
        )
      )
      .subscribe((districts: IDistrict[]) => (this.districtsSharedCollection = districts));

    this.talukaService
      .query()
      .pipe(map((res: HttpResponse<ITaluka[]>) => res.body ?? []))
      .pipe(map((talukas: ITaluka[]) => this.talukaService.addTalukaToCollectionIfMissing(talukas, this.editForm.get('taluka')!.value)))
      .subscribe((talukas: ITaluka[]) => (this.talukasSharedCollection = talukas));

    this.cityService
      .query()
      .pipe(map((res: HttpResponse<ICity[]>) => res.body ?? []))
      .pipe(map((cities: ICity[]) => this.cityService.addCityToCollectionIfMissing(cities, this.editForm.get('city')!.value)))
      .subscribe((cities: ICity[]) => (this.citiesSharedCollection = cities));

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

  protected createFromForm(): ISupplier {
    return {
      ...new Supplier(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      supplierType: this.editForm.get(['supplierType'])!.value,
      contactNo: this.editForm.get(['contactNo'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      email: this.editForm.get(['email'])!.value,
      registrationNo: this.editForm.get(['registrationNo'])!.value,
      address1: this.editForm.get(['address1'])!.value,
      address2: this.editForm.get(['address2'])!.value,
      area: this.editForm.get(['area'])!.value,
      pinCode: this.editForm.get(['pinCode'])!.value,
      statusInd: this.editForm.get(['statusInd'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      state: this.editForm.get(['state'])!.value,
      district: this.editForm.get(['district'])!.value,
      taluka: this.editForm.get(['taluka'])!.value,
      city: this.editForm.get(['city'])!.value,
      inventoryType: this.editForm.get(['inventoryType'])!.value,
    };
  }
}
