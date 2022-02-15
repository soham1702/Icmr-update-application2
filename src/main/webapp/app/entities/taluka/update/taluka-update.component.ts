import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITaluka, Taluka } from '../taluka.model';
import { TalukaService } from '../service/taluka.service';
import { IDistrict } from 'app/entities/district/district.model';
import { DistrictService } from 'app/entities/district/service/district.service';

@Component({
  selector: 'jhi-taluka-update',
  templateUrl: './taluka-update.component.html',
})
export class TalukaUpdateComponent implements OnInit {
  isSaving = false;

  districtsSharedCollection: IDistrict[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    deleted: [],
    lgdCode: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    district: [],
  });

  constructor(
    protected talukaService: TalukaService,
    protected districtService: DistrictService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taluka }) => {
      if (taluka.id === undefined) {
        const today = dayjs().startOf('day');
        taluka.lastModified = today;
      }

      this.updateForm(taluka);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taluka = this.createFromForm();
    if (taluka.id !== undefined) {
      this.subscribeToSaveResponse(this.talukaService.update(taluka));
    } else {
      this.subscribeToSaveResponse(this.talukaService.create(taluka));
    }
  }

  trackDistrictById(index: number, item: IDistrict): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaluka>>): void {
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

  protected updateForm(taluka: ITaluka): void {
    this.editForm.patchValue({
      id: taluka.id,
      name: taluka.name,
      deleted: taluka.deleted,
      lgdCode: taluka.lgdCode,
      lastModified: taluka.lastModified ? taluka.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: taluka.lastModifiedBy,
      district: taluka.district,
    });

    this.districtsSharedCollection = this.districtService.addDistrictToCollectionIfMissing(this.districtsSharedCollection, taluka.district);
  }

  protected loadRelationshipsOptions(): void {
    this.districtService
      .query()
      .pipe(map((res: HttpResponse<IDistrict[]>) => res.body ?? []))
      .pipe(
        map((districts: IDistrict[]) =>
          this.districtService.addDistrictToCollectionIfMissing(districts, this.editForm.get('district')!.value)
        )
      )
      .subscribe((districts: IDistrict[]) => (this.districtsSharedCollection = districts));
  }

  protected createFromForm(): ITaluka {
    return {
      ...new Taluka(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      lgdCode: this.editForm.get(['lgdCode'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      district: this.editForm.get(['district'])!.value,
    };
  }
}
