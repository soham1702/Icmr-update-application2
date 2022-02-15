import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IBedType, BedType } from '../bed-type.model';
import { BedTypeService } from '../service/bed-type.service';

@Component({
  selector: 'jhi-bed-type-update',
  templateUrl: './bed-type-update.component.html',
})
export class BedTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    perDayOX: [],
    deleted: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(protected bedTypeService: BedTypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bedType }) => {
      if (bedType.id === undefined) {
        const today = dayjs().startOf('day');
        bedType.lastModified = today;
      }

      this.updateForm(bedType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bedType = this.createFromForm();
    if (bedType.id !== undefined) {
      this.subscribeToSaveResponse(this.bedTypeService.update(bedType));
    } else {
      this.subscribeToSaveResponse(this.bedTypeService.create(bedType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBedType>>): void {
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

  protected updateForm(bedType: IBedType): void {
    this.editForm.patchValue({
      id: bedType.id,
      name: bedType.name,
      perDayOX: bedType.perDayOX,
      deleted: bedType.deleted,
      lastModified: bedType.lastModified ? bedType.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: bedType.lastModifiedBy,
    });
  }

  protected createFromForm(): IBedType {
    return {
      ...new BedType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      perDayOX: this.editForm.get(['perDayOX'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }
}
