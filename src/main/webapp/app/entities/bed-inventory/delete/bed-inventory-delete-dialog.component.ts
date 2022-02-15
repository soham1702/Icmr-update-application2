import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBedInventory } from '../bed-inventory.model';
import { BedInventoryService } from '../service/bed-inventory.service';

@Component({
  templateUrl: './bed-inventory-delete-dialog.component.html',
})
export class BedInventoryDeleteDialogComponent {
  bedInventory?: IBedInventory;

  constructor(protected bedInventoryService: BedInventoryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bedInventoryService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
