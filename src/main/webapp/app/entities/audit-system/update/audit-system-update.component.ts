import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IAuditSystem, AuditSystem } from '../audit-system.model';
import { AuditSystemService } from '../service/audit-system.service';
import { IAuditType } from 'app/entities/audit-type/audit-type.model';
import { AuditTypeService } from 'app/entities/audit-type/service/audit-type.service';
import { IHospital } from 'app/entities/hospital/hospital.model';
import { HospitalService } from 'app/entities/hospital/service/hospital.service';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { SupplierService } from 'app/entities/supplier/service/supplier.service';

@Component({
  selector: 'jhi-audit-system-update',
  templateUrl: './audit-system-update.component.html',
})
export class AuditSystemUpdateComponent implements OnInit {
  isSaving = false;

  auditTypesSharedCollection: IAuditType[] = [];
  hospitalsSharedCollection: IHospital[] = [];
  suppliersSharedCollection: ISupplier[] = [];

  editForm = this.fb.group({
    id: [],
    auditorName: [null, [Validators.required]],
    defectCount: [],
    defectFixCount: [],
    inspectionDate: [null, [Validators.required]],
    remark: [],
    status: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    auditType: [],
    hospital: [],
    supplier: [],
  });

  constructor(
    protected auditSystemService: AuditSystemService,
    protected auditTypeService: AuditTypeService,
    protected hospitalService: HospitalService,
    protected supplierService: SupplierService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditSystem }) => {
      if (auditSystem.id === undefined) {
        const today = dayjs().startOf('day');
        auditSystem.inspectionDate = today;
        auditSystem.lastModified = today;
      }

      this.updateForm(auditSystem);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const auditSystem = this.createFromForm();
    if (auditSystem.id !== undefined) {
      this.subscribeToSaveResponse(this.auditSystemService.update(auditSystem));
    } else {
      this.subscribeToSaveResponse(this.auditSystemService.create(auditSystem));
    }
  }

  trackAuditTypeById(index: number, item: IAuditType): number {
    return item.id!;
  }

  trackHospitalById(index: number, item: IHospital): number {
    return item.id!;
  }

  trackSupplierById(index: number, item: ISupplier): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuditSystem>>): void {
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

  protected updateForm(auditSystem: IAuditSystem): void {
    this.editForm.patchValue({
      id: auditSystem.id,
      auditorName: auditSystem.auditorName,
      defectCount: auditSystem.defectCount,
      defectFixCount: auditSystem.defectFixCount,
      inspectionDate: auditSystem.inspectionDate ? auditSystem.inspectionDate.format(DATE_TIME_FORMAT) : null,
      remark: auditSystem.remark,
      status: auditSystem.status,
      lastModified: auditSystem.lastModified ? auditSystem.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: auditSystem.lastModifiedBy,
      auditType: auditSystem.auditType,
      hospital: auditSystem.hospital,
      supplier: auditSystem.supplier,
    });

    this.auditTypesSharedCollection = this.auditTypeService.addAuditTypeToCollectionIfMissing(
      this.auditTypesSharedCollection,
      auditSystem.auditType
    );
    this.hospitalsSharedCollection = this.hospitalService.addHospitalToCollectionIfMissing(
      this.hospitalsSharedCollection,
      auditSystem.hospital
    );
    this.suppliersSharedCollection = this.supplierService.addSupplierToCollectionIfMissing(
      this.suppliersSharedCollection,
      auditSystem.supplier
    );
  }

  protected loadRelationshipsOptions(): void {
    this.auditTypeService
      .query()
      .pipe(map((res: HttpResponse<IAuditType[]>) => res.body ?? []))
      .pipe(
        map((auditTypes: IAuditType[]) =>
          this.auditTypeService.addAuditTypeToCollectionIfMissing(auditTypes, this.editForm.get('auditType')!.value)
        )
      )
      .subscribe((auditTypes: IAuditType[]) => (this.auditTypesSharedCollection = auditTypes));

    this.hospitalService
      .query()
      .pipe(map((res: HttpResponse<IHospital[]>) => res.body ?? []))
      .pipe(
        map((hospitals: IHospital[]) =>
          this.hospitalService.addHospitalToCollectionIfMissing(hospitals, this.editForm.get('hospital')!.value)
        )
      )
      .subscribe((hospitals: IHospital[]) => (this.hospitalsSharedCollection = hospitals));

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

  protected createFromForm(): IAuditSystem {
    return {
      ...new AuditSystem(),
      id: this.editForm.get(['id'])!.value,
      auditorName: this.editForm.get(['auditorName'])!.value,
      defectCount: this.editForm.get(['defectCount'])!.value,
      defectFixCount: this.editForm.get(['defectFixCount'])!.value,
      inspectionDate: this.editForm.get(['inspectionDate'])!.value
        ? dayjs(this.editForm.get(['inspectionDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      remark: this.editForm.get(['remark'])!.value,
      status: this.editForm.get(['status'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      auditType: this.editForm.get(['auditType'])!.value,
      hospital: this.editForm.get(['hospital'])!.value,
      supplier: this.editForm.get(['supplier'])!.value,
    };
  }
}
