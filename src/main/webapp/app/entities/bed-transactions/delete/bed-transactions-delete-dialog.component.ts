import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBedTransactions } from '../bed-transactions.model';
import { BedTransactionsService } from '../service/bed-transactions.service';

@Component({
  templateUrl: './bed-transactions-delete-dialog.component.html',
})
export class BedTransactionsDeleteDialogComponent {
  bedTransactions?: IBedTransactions;

  constructor(protected bedTransactionsService: BedTransactionsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bedTransactionsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
