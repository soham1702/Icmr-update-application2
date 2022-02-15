import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IContactType } from '../contact-type.model';
import { ContactTypeService } from '../service/contact-type.service';

@Component({
  templateUrl: './contact-type-delete-dialog.component.html',
})
export class ContactTypeDeleteDialogComponent {
  contactType?: IContactType;

  constructor(protected contactTypeService: ContactTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contactTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
