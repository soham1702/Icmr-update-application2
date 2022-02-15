import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITrip, Trip } from '../trip.model';
import { TripService } from '../service/trip.service';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { SupplierService } from 'app/entities/supplier/service/supplier.service';
import { TransactionStatus } from 'app/entities/enumerations/transaction-status.model';

@Component({
  selector: 'jhi-trip-update',
  templateUrl: './trip-update.component.html',
})
export class TripUpdateComponent implements OnInit {
  isSaving = false;
  transactionStatusValues = Object.keys(TransactionStatus);

  suppliersSharedCollection: ISupplier[] = [];

  editForm = this.fb.group({
    id: [],
    trackingNo: [null, [Validators.required]],
    mobaId: [null, [Validators.required]],
    numberPlate: [null, [Validators.required]],
    stock: [null, [Validators.required]],
    status: [null, [Validators.required]],
    createdDate: [null, [Validators.required]],
    createdBy: [null, [Validators.required]],
    lastModified: [],
    comment: [],
    lastModifiedBy: [],
    supplier: [],
  });

  constructor(
    protected tripService: TripService,
    protected supplierService: SupplierService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trip }) => {
      if (trip.id === undefined) {
        const today = dayjs().startOf('day');
        trip.createdDate = today;
        trip.lastModified = today;
      }

      this.updateForm(trip);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trip = this.createFromForm();
    if (trip.id !== undefined) {
      this.subscribeToSaveResponse(this.tripService.update(trip));
    } else {
      this.subscribeToSaveResponse(this.tripService.create(trip));
    }
  }

  trackSupplierById(index: number, item: ISupplier): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrip>>): void {
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

  protected updateForm(trip: ITrip): void {
    this.editForm.patchValue({
      id: trip.id,
      trackingNo: trip.trackingNo,
      mobaId: trip.mobaId,
      numberPlate: trip.numberPlate,
      stock: trip.stock,
      status: trip.status,
      createdDate: trip.createdDate ? trip.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: trip.createdBy,
      lastModified: trip.lastModified ? trip.lastModified.format(DATE_TIME_FORMAT) : null,
      comment: trip.comment,
      lastModifiedBy: trip.lastModifiedBy,
      supplier: trip.supplier,
    });

    this.suppliersSharedCollection = this.supplierService.addSupplierToCollectionIfMissing(this.suppliersSharedCollection, trip.supplier);
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
  }

  protected createFromForm(): ITrip {
    return {
      ...new Trip(),
      id: this.editForm.get(['id'])!.value,
      trackingNo: this.editForm.get(['trackingNo'])!.value,
      mobaId: this.editForm.get(['mobaId'])!.value,
      numberPlate: this.editForm.get(['numberPlate'])!.value,
      stock: this.editForm.get(['stock'])!.value,
      status: this.editForm.get(['status'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      comment: this.editForm.get(['comment'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      supplier: this.editForm.get(['supplier'])!.value,
    };
  }
}
