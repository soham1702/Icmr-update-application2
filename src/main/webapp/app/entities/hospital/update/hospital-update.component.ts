import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IHospital, Hospital } from '../hospital.model';
import { HospitalService } from '../service/hospital.service';
import { IState } from 'app/entities/state/state.model';
import { StateService } from 'app/entities/state/service/state.service';
import { IDistrict } from 'app/entities/district/district.model';
import { DistrictService } from 'app/entities/district/service/district.service';
import { ITaluka } from 'app/entities/taluka/taluka.model';
import { TalukaService } from 'app/entities/taluka/service/taluka.service';
import { ICity } from 'app/entities/city/city.model';
import { CityService } from 'app/entities/city/service/city.service';
import { IMunicipalCorp } from 'app/entities/municipal-corp/municipal-corp.model';
import { MunicipalCorpService } from 'app/entities/municipal-corp/service/municipal-corp.service';
import { IHospitalType } from 'app/entities/hospital-type/hospital-type.model';
import { HospitalTypeService } from 'app/entities/hospital-type/service/hospital-type.service';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { SupplierService } from 'app/entities/supplier/service/supplier.service';
import { HospitalCategory } from 'app/entities/enumerations/hospital-category.model';
import { HospitalSubCategory } from 'app/entities/enumerations/hospital-sub-category.model';
import { StatusInd } from 'app/entities/enumerations/status-ind.model';

@Component({
  selector: 'jhi-hospital-update',
  templateUrl: './hospital-update.component.html',
})
export class HospitalUpdateComponent implements OnInit {
  isSaving = false;
  hospitalCategoryValues = Object.keys(HospitalCategory);
  hospitalSubCategoryValues = Object.keys(HospitalSubCategory);
  statusIndValues = Object.keys(StatusInd);

  statesSharedCollection: IState[] = [];
  districtsSharedCollection: IDistrict[] = [];
  talukasSharedCollection: ITaluka[] = [];
  citiesSharedCollection: ICity[] = [];
  municipalCorpsSharedCollection: IMunicipalCorp[] = [];
  hospitalTypesSharedCollection: IHospitalType[] = [];
  suppliersSharedCollection: ISupplier[] = [];

  editForm = this.fb.group({
    id: [],
    category: [null, [Validators.required]],
    subCategory: [null, [Validators.required]],
    contactNo: [],
    latitude: [],
    longitude: [],
    docCount: [],
    email: [],
    name: [null, [Validators.required]],
    registrationNo: [],
    address1: [],
    address2: [],
    area: [],
    pinCode: [null, [Validators.required]],
    hospitalId: [],
    odasFacilityId: [],
    referenceNumber: [],
    statusInd: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    state: [],
    district: [],
    taluka: [],
    city: [],
    municipalCorp: [],
    hospitalType: [],
    suppliers: [],
  });

  constructor(
    protected hospitalService: HospitalService,
    protected stateService: StateService,
    protected districtService: DistrictService,
    protected talukaService: TalukaService,
    protected cityService: CityService,
    protected municipalCorpService: MunicipalCorpService,
    protected hospitalTypeService: HospitalTypeService,
    protected supplierService: SupplierService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hospital }) => {
      if (hospital.id === undefined) {
        const today = dayjs().startOf('day');
        hospital.lastModified = today;
      }

      this.updateForm(hospital);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hospital = this.createFromForm();
    if (hospital.id !== undefined) {
      this.subscribeToSaveResponse(this.hospitalService.update(hospital));
    } else {
      this.subscribeToSaveResponse(this.hospitalService.create(hospital));
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

  trackMunicipalCorpById(index: number, item: IMunicipalCorp): number {
    return item.id!;
  }

  trackHospitalTypeById(index: number, item: IHospitalType): number {
    return item.id!;
  }

  trackSupplierById(index: number, item: ISupplier): number {
    return item.id!;
  }

  getSelectedSupplier(option: ISupplier, selectedVals?: ISupplier[]): ISupplier {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHospital>>): void {
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

  protected updateForm(hospital: IHospital): void {
    this.editForm.patchValue({
      id: hospital.id,
      category: hospital.category,
      subCategory: hospital.subCategory,
      contactNo: hospital.contactNo,
      latitude: hospital.latitude,
      longitude: hospital.longitude,
      docCount: hospital.docCount,
      email: hospital.email,
      name: hospital.name,
      registrationNo: hospital.registrationNo,
      address1: hospital.address1,
      address2: hospital.address2,
      area: hospital.area,
      pinCode: hospital.pinCode,
      hospitalId: hospital.hospitalId,
      odasFacilityId: hospital.odasFacilityId,
      referenceNumber: hospital.referenceNumber,
      statusInd: hospital.statusInd,
      lastModified: hospital.lastModified ? hospital.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: hospital.lastModifiedBy,
      state: hospital.state,
      district: hospital.district,
      taluka: hospital.taluka,
      city: hospital.city,
      municipalCorp: hospital.municipalCorp,
      hospitalType: hospital.hospitalType,
      suppliers: hospital.suppliers,
    });

    this.statesSharedCollection = this.stateService.addStateToCollectionIfMissing(this.statesSharedCollection, hospital.state);
    this.districtsSharedCollection = this.districtService.addDistrictToCollectionIfMissing(
      this.districtsSharedCollection,
      hospital.district
    );
    this.talukasSharedCollection = this.talukaService.addTalukaToCollectionIfMissing(this.talukasSharedCollection, hospital.taluka);
    this.citiesSharedCollection = this.cityService.addCityToCollectionIfMissing(this.citiesSharedCollection, hospital.city);
    this.municipalCorpsSharedCollection = this.municipalCorpService.addMunicipalCorpToCollectionIfMissing(
      this.municipalCorpsSharedCollection,
      hospital.municipalCorp
    );
    this.hospitalTypesSharedCollection = this.hospitalTypeService.addHospitalTypeToCollectionIfMissing(
      this.hospitalTypesSharedCollection,
      hospital.hospitalType
    );
    this.suppliersSharedCollection = this.supplierService.addSupplierToCollectionIfMissing(
      this.suppliersSharedCollection,
      ...(hospital.suppliers ?? [])
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

    this.municipalCorpService
      .query()
      .pipe(map((res: HttpResponse<IMunicipalCorp[]>) => res.body ?? []))
      .pipe(
        map((municipalCorps: IMunicipalCorp[]) =>
          this.municipalCorpService.addMunicipalCorpToCollectionIfMissing(municipalCorps, this.editForm.get('municipalCorp')!.value)
        )
      )
      .subscribe((municipalCorps: IMunicipalCorp[]) => (this.municipalCorpsSharedCollection = municipalCorps));

    this.hospitalTypeService
      .query()
      .pipe(map((res: HttpResponse<IHospitalType[]>) => res.body ?? []))
      .pipe(
        map((hospitalTypes: IHospitalType[]) =>
          this.hospitalTypeService.addHospitalTypeToCollectionIfMissing(hospitalTypes, this.editForm.get('hospitalType')!.value)
        )
      )
      .subscribe((hospitalTypes: IHospitalType[]) => (this.hospitalTypesSharedCollection = hospitalTypes));

    this.supplierService
      .query()
      .pipe(map((res: HttpResponse<ISupplier[]>) => res.body ?? []))
      .pipe(
        map((suppliers: ISupplier[]) =>
          this.supplierService.addSupplierToCollectionIfMissing(suppliers, ...(this.editForm.get('suppliers')!.value ?? []))
        )
      )
      .subscribe((suppliers: ISupplier[]) => (this.suppliersSharedCollection = suppliers));
  }

  protected createFromForm(): IHospital {
    return {
      ...new Hospital(),
      id: this.editForm.get(['id'])!.value,
      category: this.editForm.get(['category'])!.value,
      subCategory: this.editForm.get(['subCategory'])!.value,
      contactNo: this.editForm.get(['contactNo'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      docCount: this.editForm.get(['docCount'])!.value,
      email: this.editForm.get(['email'])!.value,
      name: this.editForm.get(['name'])!.value,
      registrationNo: this.editForm.get(['registrationNo'])!.value,
      address1: this.editForm.get(['address1'])!.value,
      address2: this.editForm.get(['address2'])!.value,
      area: this.editForm.get(['area'])!.value,
      pinCode: this.editForm.get(['pinCode'])!.value,
      hospitalId: this.editForm.get(['hospitalId'])!.value,
      odasFacilityId: this.editForm.get(['odasFacilityId'])!.value,
      referenceNumber: this.editForm.get(['referenceNumber'])!.value,
      statusInd: this.editForm.get(['statusInd'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      state: this.editForm.get(['state'])!.value,
      district: this.editForm.get(['district'])!.value,
      taluka: this.editForm.get(['taluka'])!.value,
      city: this.editForm.get(['city'])!.value,
      municipalCorp: this.editForm.get(['municipalCorp'])!.value,
      hospitalType: this.editForm.get(['hospitalType'])!.value,
      suppliers: this.editForm.get(['suppliers'])!.value,
    };
  }
}
