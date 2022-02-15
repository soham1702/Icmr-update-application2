import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransactions } from '../transactions.model';
import { TransactionsService } from '../service/transactions.service';

@Component({
  templateUrl: './transactions-delete-dialog.component.html',
})
export class TransactionsDeleteDialogComponent {
  transactions?: ITransactions;

  constructor(protected transactionsService: TransactionsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transactionsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
