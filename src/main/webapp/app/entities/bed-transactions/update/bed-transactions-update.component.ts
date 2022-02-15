import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IBedTransactions, BedTransactions } from '../bed-transactions.model';
import { BedTransactionsService } from '../service/bed-transactions.service';
import { IBedType } from 'app/entities/bed-type/bed-type.model';
import { BedTypeService } from 'app/entities/bed-type/service/bed-type.service';
import { IHospital } from 'app/entities/hospital/hospital.model';
import { HospitalService } from 'app/entities/hospital/service/hospital.service';

@Component({
  selector: 'jhi-bed-transactions-update',
  templateUrl: './bed-transactions-update.component.html',
})
export class BedTransactionsUpdateComponent implements OnInit {
  isSaving = false;

  bedTypesSharedCollection: IBedType[] = [];
  hospitalsSharedCollection: IHospital[] = [];

  editForm = this.fb.group({
    id: [],
    occupied: [null, [Validators.required]],
    onCylinder: [],
    onLMO: [],
    onConcentrators: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    bedType: [],
    hospital: [],
  });

  constructor(
    protected bedTransactionsService: BedTransactionsService,
    protected bedTypeService: BedTypeService,
    protected hospitalService: HospitalService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bedTransactions }) => {
      if (bedTransactions.id === undefined) {
        const today = dayjs().startOf('day');
        bedTransactions.lastModified = today;
      }

      this.updateForm(bedTransactions);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bedTransactions = this.createFromForm();
    if (bedTransactions.id !== undefined) {
      this.subscribeToSaveResponse(this.bedTransactionsService.update(bedTransactions));
    } else {
      this.subscribeToSaveResponse(this.bedTransactionsService.create(bedTransactions));
    }
  }

  trackBedTypeById(index: number, item: IBedType): number {
    return item.id!;
  }

  trackHospitalById(index: number, item: IHospital): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBedTransactions>>): void {
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

  protected updateForm(bedTransactions: IBedTransactions): void {
    this.editForm.patchValue({
      id: bedTransactions.id,
      occupied: bedTransactions.occupied,
      onCylinder: bedTransactions.onCylinder,
      onLMO: bedTransactions.onLMO,
      onConcentrators: bedTransactions.onConcentrators,
      lastModified: bedTransactions.lastModified ? bedTransactions.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: bedTransactions.lastModifiedBy,
      bedType: bedTransactions.bedType,
      hospital: bedTransactions.hospital,
    });

    this.bedTypesSharedCollection = this.bedTypeService.addBedTypeToCollectionIfMissing(
      this.bedTypesSharedCollection,
      bedTransactions.bedType
    );
    this.hospitalsSharedCollection = this.hospitalService.addHospitalToCollectionIfMissing(
      this.hospitalsSharedCollection,
      bedTransactions.hospital
    );
  }

  protected loadRelationshipsOptions(): void {
    this.bedTypeService
      .query()
      .pipe(map((res: HttpResponse<IBedType[]>) => res.body ?? []))
      .pipe(
        map((bedTypes: IBedType[]) => this.bedTypeService.addBedTypeToCollectionIfMissing(bedTypes, this.editForm.get('bedType')!.value))
      )
      .subscribe((bedTypes: IBedType[]) => (this.bedTypesSharedCollection = bedTypes));

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

  protected createFromForm(): IBedTransactions {
    return {
      ...new BedTransactions(),
      id: this.editForm.get(['id'])!.value,
      occupied: this.editForm.get(['occupied'])!.value,
      onCylinder: this.editForm.get(['onCylinder'])!.value,
      onLMO: this.editForm.get(['onLMO'])!.value,
      onConcentrators: this.editForm.get(['onConcentrators'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      bedType: this.editForm.get(['bedType'])!.value,
      hospital: this.editForm.get(['hospital'])!.value,
    };
  }
}
