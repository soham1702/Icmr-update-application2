import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITripDetails, TripDetails } from '../trip-details.model';
import { TripDetailsService } from '../service/trip-details.service';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { SupplierService } from 'app/entities/supplier/service/supplier.service';
import { IHospital } from 'app/entities/hospital/hospital.model';
import { HospitalService } from 'app/entities/hospital/service/hospital.service';
import { ITransactions } from 'app/entities/transactions/transactions.model';
import { TransactionsService } from 'app/entities/transactions/service/transactions.service';
import { ITrip } from 'app/entities/trip/trip.model';
import { TripService } from 'app/entities/trip/service/trip.service';
import { TransactionStatus } from 'app/entities/enumerations/transaction-status.model';

@Component({
  selector: 'jhi-trip-details-update',
  templateUrl: './trip-details-update.component.html',
})
export class TripDetailsUpdateComponent implements OnInit {
  isSaving = false;
  transactionStatusValues = Object.keys(TransactionStatus);

  suppliersSharedCollection: ISupplier[] = [];
  hospitalsSharedCollection: IHospital[] = [];
  transactionsSharedCollection: ITransactions[] = [];
  tripsSharedCollection: ITrip[] = [];

  editForm = this.fb.group({
    id: [],
    stockSent: [null, [Validators.required]],
    stockRec: [],
    comment: [],
    receiverComment: [],
    status: [null, [Validators.required]],
    createdDate: [null, [Validators.required]],
    createdBy: [null, [Validators.required]],
    lastModified: [],
    lastModifiedBy: [],
    supplier: [],
    hospital: [],
    transactions: [],
    trip: [],
  });

  constructor(
    protected tripDetailsService: TripDetailsService,
    protected supplierService: SupplierService,
    protected hospitalService: HospitalService,
    protected transactionsService: TransactionsService,
    protected tripService: TripService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tripDetails }) => {
      if (tripDetails.id === undefined) {
        const today = dayjs().startOf('day');
        tripDetails.createdDate = today;
        tripDetails.lastModified = today;
      }

      this.updateForm(tripDetails);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tripDetails = this.createFromForm();
    if (tripDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.tripDetailsService.update(tripDetails));
    } else {
      this.subscribeToSaveResponse(this.tripDetailsService.create(tripDetails));
    }
  }

  trackSupplierById(index: number, item: ISupplier): number {
    return item.id!;
  }

  trackHospitalById(index: number, item: IHospital): number {
    return item.id!;
  }

  trackTransactionsById(index: number, item: ITransactions): number {
    return item.id!;
  }

  trackTripById(index: number, item: ITrip): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITripDetails>>): void {
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

  protected updateForm(tripDetails: ITripDetails): void {
    this.editForm.patchValue({
      id: tripDetails.id,
      stockSent: tripDetails.stockSent,
      stockRec: tripDetails.stockRec,
      comment: tripDetails.comment,
      receiverComment: tripDetails.receiverComment,
      status: tripDetails.status,
      createdDate: tripDetails.createdDate ? tripDetails.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: tripDetails.createdBy,
      lastModified: tripDetails.lastModified ? tripDetails.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: tripDetails.lastModifiedBy,
      supplier: tripDetails.supplier,
      hospital: tripDetails.hospital,
      transactions: tripDetails.transactions,
      trip: tripDetails.trip,
    });

    this.suppliersSharedCollection = this.supplierService.addSupplierToCollectionIfMissing(
      this.suppliersSharedCollection,
      tripDetails.supplier
    );
    this.hospitalsSharedCollection = this.hospitalService.addHospitalToCollectionIfMissing(
      this.hospitalsSharedCollection,
      tripDetails.hospital
    );
    this.transactionsSharedCollection = this.transactionsService.addTransactionsToCollectionIfMissing(
      this.transactionsSharedCollection,
      tripDetails.transactions
    );
    this.tripsSharedCollection = this.tripService.addTripToCollectionIfMissing(this.tripsSharedCollection, tripDetails.trip);
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

    this.hospitalService
      .query()
      .pipe(map((res: HttpResponse<IHospital[]>) => res.body ?? []))
      .pipe(
        map((hospitals: IHospital[]) =>
          this.hospitalService.addHospitalToCollectionIfMissing(hospitals, this.editForm.get('hospital')!.value)
        )
      )
      .subscribe((hospitals: IHospital[]) => (this.hospitalsSharedCollection = hospitals));

    this.transactionsService
      .query()
      .pipe(map((res: HttpResponse<ITransactions[]>) => res.body ?? []))
      .pipe(
        map((transactions: ITransactions[]) =>
          this.transactionsService.addTransactionsToCollectionIfMissing(transactions, this.editForm.get('transactions')!.value)
        )
      )
      .subscribe((transactions: ITransactions[]) => (this.transactionsSharedCollection = transactions));

    this.tripService
      .query()
      .pipe(map((res: HttpResponse<ITrip[]>) => res.body ?? []))
      .pipe(map((trips: ITrip[]) => this.tripService.addTripToCollectionIfMissing(trips, this.editForm.get('trip')!.value)))
      .subscribe((trips: ITrip[]) => (this.tripsSharedCollection = trips));
  }

  protected createFromForm(): ITripDetails {
    return {
      ...new TripDetails(),
      id: this.editForm.get(['id'])!.value,
      stockSent: this.editForm.get(['stockSent'])!.value,
      stockRec: this.editForm.get(['stockRec'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      receiverComment: this.editForm.get(['receiverComment'])!.value,
      status: this.editForm.get(['status'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      supplier: this.editForm.get(['supplier'])!.value,
      hospital: this.editForm.get(['hospital'])!.value,
      transactions: this.editForm.get(['transactions'])!.value,
      trip: this.editForm.get(['trip'])!.value,
    };
  }
}
