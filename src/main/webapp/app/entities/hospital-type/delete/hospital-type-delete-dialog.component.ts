import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHospitalType } from '../hospital-type.model';
import { HospitalTypeService } from '../service/hospital-type.service';

@Component({
  templateUrl: './hospital-type-delete-dialog.component.html',
})
export class HospitalTypeDeleteDialogComponent {
  hospitalType?: IHospitalType;

  constructor(protected hospitalTypeService: HospitalTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hospitalTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
