import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IICMRDailyCount, ICMRDailyCount } from '../icmr-daily-count.model';
import { ICMRDailyCountService } from '../service/icmr-daily-count.service';

@Component({
  selector: 'jhi-icmr-daily-count-update',
  templateUrl: './icmr-daily-count-update.component.html',
})
export class ICMRDailyCountUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    totalSamplesTested: [],
    totalNegative: [],
    totalPositive: [],
    currentPreviousMonthTest: [],
    districtId: [],
    remarks: [],
    editedOn: [],
    updatedDate: [],
    freeField1: [],
    freeField2: [],
  });

  constructor(
    protected iCMRDailyCountService: ICMRDailyCountService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ iCMRDailyCount }) => {
      if (iCMRDailyCount.id === undefined) {
        const today = dayjs().startOf('day');
        iCMRDailyCount.editedOn = today;
        iCMRDailyCount.updatedDate = today;
      }

      this.updateForm(iCMRDailyCount);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const iCMRDailyCount = this.createFromForm();
    if (iCMRDailyCount.id !== undefined) {
      this.subscribeToSaveResponse(this.iCMRDailyCountService.update(iCMRDailyCount));
    } else {
      this.subscribeToSaveResponse(this.iCMRDailyCountService.create(iCMRDailyCount));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IICMRDailyCount>>): void {
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

  protected updateForm(iCMRDailyCount: IICMRDailyCount): void {
    this.editForm.patchValue({
      id: iCMRDailyCount.id,
      totalSamplesTested: iCMRDailyCount.totalSamplesTested,
      totalNegative: iCMRDailyCount.totalNegative,
      totalPositive: iCMRDailyCount.totalPositive,
      currentPreviousMonthTest: iCMRDailyCount.currentPreviousMonthTest,
      districtId: iCMRDailyCount.districtId,
      remarks: iCMRDailyCount.remarks,
      editedOn: iCMRDailyCount.editedOn ? iCMRDailyCount.editedOn.format(DATE_TIME_FORMAT) : null,
      updatedDate: iCMRDailyCount.updatedDate ? iCMRDailyCount.updatedDate.format(DATE_TIME_FORMAT) : null,
      freeField1: iCMRDailyCount.freeField1,
      freeField2: iCMRDailyCount.freeField2,
    });
  }

  protected createFromForm(): IICMRDailyCount {
    return {
      ...new ICMRDailyCount(),
      id: this.editForm.get(['id'])!.value,
      totalSamplesTested: this.editForm.get(['totalSamplesTested'])!.value,
      totalNegative: this.editForm.get(['totalNegative'])!.value,
      totalPositive: this.editForm.get(['totalPositive'])!.value,
      currentPreviousMonthTest: this.editForm.get(['currentPreviousMonthTest'])!.value,
      districtId: this.editForm.get(['districtId'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      editedOn: this.editForm.get(['editedOn'])!.value ? dayjs(this.editForm.get(['editedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? dayjs(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
    };
  }
}
