import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHospital } from '../hospital.model';
import { HospitalService } from '../service/hospital.service';

@Component({
  templateUrl: './hospital-delete-dialog.component.html',
})
export class HospitalDeleteDialogComponent {
  hospital?: IHospital;

  constructor(protected hospitalService: HospitalService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hospitalService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
