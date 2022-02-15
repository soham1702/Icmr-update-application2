import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IHospitalType, HospitalType } from '../hospital-type.model';
import { HospitalTypeService } from '../service/hospital-type.service';

@Component({
  selector: 'jhi-hospital-type-update',
  templateUrl: './hospital-type-update.component.html',
})
export class HospitalTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    desciption: [],
    deleted: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(protected hospitalTypeService: HospitalTypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hospitalType }) => {
      if (hospitalType.id === undefined) {
        const today = dayjs().startOf('day');
        hospitalType.lastModified = today;
      }

      this.updateForm(hospitalType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hospitalType = this.createFromForm();
    if (hospitalType.id !== undefined) {
      this.subscribeToSaveResponse(this.hospitalTypeService.update(hospitalType));
    } else {
      this.subscribeToSaveResponse(this.hospitalTypeService.create(hospitalType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHospitalType>>): void {
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

  protected updateForm(hospitalType: IHospitalType): void {
    this.editForm.patchValue({
      id: hospitalType.id,
      name: hospitalType.name,
      desciption: hospitalType.desciption,
      deleted: hospitalType.deleted,
      lastModified: hospitalType.lastModified ? hospitalType.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: hospitalType.lastModifiedBy,
    });
  }

  protected createFromForm(): IHospitalType {
    return {
      ...new HospitalType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      desciption: this.editForm.get(['desciption'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }
}
