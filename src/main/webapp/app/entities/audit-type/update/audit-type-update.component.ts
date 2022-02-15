import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IAuditType, AuditType } from '../audit-type.model';
import { AuditTypeService } from '../service/audit-type.service';

@Component({
  selector: 'jhi-audit-type-update',
  templateUrl: './audit-type-update.component.html',
})
export class AuditTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    deleted: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(protected auditTypeService: AuditTypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditType }) => {
      if (auditType.id === undefined) {
        const today = dayjs().startOf('day');
        auditType.lastModified = today;
      }

      this.updateForm(auditType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const auditType = this.createFromForm();
    if (auditType.id !== undefined) {
      this.subscribeToSaveResponse(this.auditTypeService.update(auditType));
    } else {
      this.subscribeToSaveResponse(this.auditTypeService.create(auditType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuditType>>): void {
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

  protected updateForm(auditType: IAuditType): void {
    this.editForm.patchValue({
      id: auditType.id,
      name: auditType.name,
      deleted: auditType.deleted,
      lastModified: auditType.lastModified ? auditType.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: auditType.lastModifiedBy,
    });
  }

  protected createFromForm(): IAuditType {
    return {
      ...new AuditType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }
}
