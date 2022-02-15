import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AuditSystemComponent } from './list/audit-system.component';
import { AuditSystemDetailComponent } from './detail/audit-system-detail.component';
import { AuditSystemUpdateComponent } from './update/audit-system-update.component';
import { AuditSystemDeleteDialogComponent } from './delete/audit-system-delete-dialog.component';
import { AuditSystemRoutingModule } from './route/audit-system-routing.module';

@NgModule({
  imports: [SharedModule, AuditSystemRoutingModule],
  declarations: [AuditSystemComponent, AuditSystemDetailComponent, AuditSystemUpdateComponent, AuditSystemDeleteDialogComponent],
  entryComponents: [AuditSystemDeleteDialogComponent],
})
export class AuditSystemModule {}
