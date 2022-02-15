import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPatientInfo } from '../patient-info.model';
import { PatientInfoService } from '../service/patient-info.service';

@Component({
  templateUrl: './patient-info-delete-dialog.component.html',
})
export class PatientInfoDeleteDialogComponent {
  patientInfo?: IPatientInfo;

  constructor(protected patientInfoService: PatientInfoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.patientInfoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
