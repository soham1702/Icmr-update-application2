import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IICMRDailyCount } from '../icmr-daily-count.model';
import { ICMRDailyCountService } from '../service/icmr-daily-count.service';

@Component({
  templateUrl: './icmr-daily-count-delete-dialog.component.html',
})
export class ICMRDailyCountDeleteDialogComponent {
  iCMRDailyCount?: IICMRDailyCount;

  constructor(protected iCMRDailyCountService: ICMRDailyCountService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.iCMRDailyCountService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
