import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAuditType } from '../audit-type.model';
import { AuditTypeService } from '../service/audit-type.service';

@Component({
  templateUrl: './audit-type-delete-dialog.component.html',
})
export class AuditTypeDeleteDialogComponent {
  auditType?: IAuditType;

  constructor(protected auditTypeService: AuditTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.auditTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
