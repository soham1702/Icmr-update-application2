import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaluka } from '../taluka.model';
import { TalukaService } from '../service/taluka.service';

@Component({
  templateUrl: './taluka-delete-dialog.component.html',
})
export class TalukaDeleteDialogComponent {
  taluka?: ITaluka;

  constructor(protected talukaService: TalukaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.talukaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
