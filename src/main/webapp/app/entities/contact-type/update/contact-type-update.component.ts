import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IContactType, ContactType } from '../contact-type.model';
import { ContactTypeService } from '../service/contact-type.service';

@Component({
  selector: 'jhi-contact-type-update',
  templateUrl: './contact-type-update.component.html',
})
export class ContactTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    deleted: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(protected contactTypeService: ContactTypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactType }) => {
      if (contactType.id === undefined) {
        const today = dayjs().startOf('day');
        contactType.lastModified = today;
      }

      this.updateForm(contactType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contactType = this.createFromForm();
    if (contactType.id !== undefined) {
      this.subscribeToSaveResponse(this.contactTypeService.update(contactType));
    } else {
      this.subscribeToSaveResponse(this.contactTypeService.create(contactType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactType>>): void {
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

  protected updateForm(contactType: IContactType): void {
    this.editForm.patchValue({
      id: contactType.id,
      name: contactType.name,
      deleted: contactType.deleted,
      lastModified: contactType.lastModified ? contactType.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: contactType.lastModifiedBy,
    });
  }

  protected createFromForm(): IContactType {
    return {
      ...new ContactType(),
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
