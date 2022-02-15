import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IMunicipalCorp, MunicipalCorp } from '../municipal-corp.model';
import { MunicipalCorpService } from '../service/municipal-corp.service';
import { IDistrict } from 'app/entities/district/district.model';
import { DistrictService } from 'app/entities/district/service/district.service';

@Component({
  selector: 'jhi-municipal-corp-update',
  templateUrl: './municipal-corp-update.component.html',
})
export class MunicipalCorpUpdateComponent implements OnInit {
  isSaving = false;

  districtsSharedCollection: IDistrict[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    deleted: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    district: [],
  });

  constructor(
    protected municipalCorpService: MunicipalCorpService,
    protected districtService: DistrictService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ municipalCorp }) => {
      if (municipalCorp.id === undefined) {
        const today = dayjs().startOf('day');
        municipalCorp.lastModified = today;
      }

      this.updateForm(municipalCorp);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const municipalCorp = this.createFromForm();
    if (municipalCorp.id !== undefined) {
      this.subscribeToSaveResponse(this.municipalCorpService.update(municipalCorp));
    } else {
      this.subscribeToSaveResponse(this.municipalCorpService.create(municipalCorp));
    }
  }

  trackDistrictById(index: number, item: IDistrict): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMunicipalCorp>>): void {
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

  protected updateForm(municipalCorp: IMunicipalCorp): void {
    this.editForm.patchValue({
      id: municipalCorp.id,
      name: municipalCorp.name,
      deleted: municipalCorp.deleted,
      lastModified: municipalCorp.lastModified ? municipalCorp.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: municipalCorp.lastModifiedBy,
      district: municipalCorp.district,
    });

    this.districtsSharedCollection = this.districtService.addDistrictToCollectionIfMissing(
      this.districtsSharedCollection,
      municipalCorp.district
    );
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

  protected createFromForm(): IMunicipalCorp {
    return {
      ...new MunicipalCorp(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      district: this.editForm.get(['district'])!.value,
    };
  }
}
