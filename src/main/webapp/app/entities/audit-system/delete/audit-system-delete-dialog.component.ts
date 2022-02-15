import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAuditSystem } from '../audit-system.model';
import { AuditSystemService } from '../service/audit-system.service';

@Component({
  templateUrl: './audit-system-delete-dialog.component.html',
})
export class AuditSystemDeleteDialogComponent {
  auditSystem?: IAuditSystem;

  constructor(protected auditSystemService: AuditSystemService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.auditSystemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
