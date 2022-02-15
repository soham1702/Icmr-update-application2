import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBedType } from '../bed-type.model';
import { BedTypeService } from '../service/bed-type.service';

@Component({
  templateUrl: './bed-type-delete-dialog.component.html',
})
export class BedTypeDeleteDialogComponent {
  bedType?: IBedType;

  constructor(protected bedTypeService: BedTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bedTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
