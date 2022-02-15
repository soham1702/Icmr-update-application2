import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IContact, Contact } from '../contact.model';
import { ContactService } from '../service/contact.service';
import { IContactType } from 'app/entities/contact-type/contact-type.model';
import { ContactTypeService } from 'app/entities/contact-type/service/contact-type.service';
import { IHospital } from 'app/entities/hospital/hospital.model';
import { HospitalService } from 'app/entities/hospital/service/hospital.service';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { SupplierService } from 'app/entities/supplier/service/supplier.service';

@Component({
  selector: 'jhi-contact-update',
  templateUrl: './contact-update.component.html',
})
export class ContactUpdateComponent implements OnInit {
  isSaving = false;

  contactTypesSharedCollection: IContactType[] = [];
  hospitalsSharedCollection: IHospital[] = [];
  suppliersSharedCollection: ISupplier[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    contactNo: [],
    email: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    contactType: [],
    hospital: [],
    supplier: [],
  });

  constructor(
    protected contactService: ContactService,
    protected contactTypeService: ContactTypeService,
    protected hospitalService: HospitalService,
    protected supplierService: SupplierService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contact }) => {
      if (contact.id === undefined) {
        const today = dayjs().startOf('day');
        contact.lastModified = today;
      }

      this.updateForm(contact);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contact = this.createFromForm();
    if (contact.id !== undefined) {
      this.subscribeToSaveResponse(this.contactService.update(contact));
    } else {
      this.subscribeToSaveResponse(this.contactService.create(contact));
    }
  }

  trackContactTypeById(index: number, item: IContactType): number {
    return item.id!;
  }

  trackHospitalById(index: number, item: IHospital): number {
    return item.id!;
  }

  trackSupplierById(index: number, item: ISupplier): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContact>>): void {
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

  protected updateForm(contact: IContact): void {
    this.editForm.patchValue({
      id: contact.id,
      name: contact.name,
      contactNo: contact.contactNo,
      email: contact.email,
      lastModified: contact.lastModified ? contact.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: contact.lastModifiedBy,
      contactType: contact.contactType,
      hospital: contact.hospital,
      supplier: contact.supplier,
    });

    this.contactTypesSharedCollection = this.contactTypeService.addContactTypeToCollectionIfMissing(
      this.contactTypesSharedCollection,
      contact.contactType
    );
    this.hospitalsSharedCollection = this.hospitalService.addHospitalToCollectionIfMissing(
      this.hospitalsSharedCollection,
      contact.hospital
    );
    this.suppliersSharedCollection = this.supplierService.addSupplierToCollectionIfMissing(
      this.suppliersSharedCollection,
      contact.supplier
    );
  }

  protected loadRelationshipsOptions(): void {
    this.contactTypeService
      .query()
      .pipe(map((res: HttpResponse<IContactType[]>) => res.body ?? []))
      .pipe(
        map((contactTypes: IContactType[]) =>
          this.contactTypeService.addContactTypeToCollectionIfMissing(contactTypes, this.editForm.get('contactType')!.value)
        )
      )
      .subscribe((contactTypes: IContactType[]) => (this.contactTypesSharedCollection = contactTypes));

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

  protected createFromForm(): IContact {
    return {
      ...new Contact(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      contactNo: this.editForm.get(['contactNo'])!.value,
      email: this.editForm.get(['email'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      contactType: this.editForm.get(['contactType'])!.value,
      hospital: this.editForm.get(['hospital'])!.value,
      supplier: this.editForm.get(['supplier'])!.value,
    };
  }
}
