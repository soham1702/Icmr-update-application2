import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IInventoryType } from '../inventory-type.model';
import { InventoryTypeService } from '../service/inventory-type.service';

@Component({
  templateUrl: './inventory-type-delete-dialog.component.html',
})
export class InventoryTypeDeleteDialogComponent {
  inventoryType?: IInventoryType;

  constructor(protected inventoryTypeService: InventoryTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inventoryTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
