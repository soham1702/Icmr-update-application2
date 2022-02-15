import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IInventoryMaster } from '../inventory-master.model';
import { InventoryMasterService } from '../service/inventory-master.service';

@Component({
  templateUrl: './inventory-master-delete-dialog.component.html',
})
export class InventoryMasterDeleteDialogComponent {
  inventoryMaster?: IInventoryMaster;

  constructor(protected inventoryMasterService: InventoryMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inventoryMasterService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
