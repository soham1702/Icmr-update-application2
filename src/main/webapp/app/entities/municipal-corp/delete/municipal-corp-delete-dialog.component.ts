import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMunicipalCorp } from '../municipal-corp.model';
import { MunicipalCorpService } from '../service/municipal-corp.service';

@Component({
  templateUrl: './municipal-corp-delete-dialog.component.html',
})
export class MunicipalCorpDeleteDialogComponent {
  municipalCorp?: IMunicipalCorp;

  constructor(protected municipalCorpService: MunicipalCorpService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.municipalCorpService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
