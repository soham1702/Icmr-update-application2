import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IInventoryUsed } from '../inventory-used.model';
import { InventoryUsedService } from '../service/inventory-used.service';

@Component({
  templateUrl: './inventory-used-delete-dialog.component.html',
})
export class InventoryUsedDeleteDialogComponent {
  inventoryUsed?: IInventoryUsed;

  constructor(protected inventoryUsedService: InventoryUsedService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inventoryUsedService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
