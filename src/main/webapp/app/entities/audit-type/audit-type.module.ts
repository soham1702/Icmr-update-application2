import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AuditTypeComponent } from './list/audit-type.component';
import { AuditTypeDetailComponent } from './detail/audit-type-detail.component';
import { AuditTypeUpdateComponent } from './update/audit-type-update.component';
import { AuditTypeDeleteDialogComponent } from './delete/audit-type-delete-dialog.component';
import { AuditTypeRoutingModule } from './route/audit-type-routing.module';

@NgModule({
  imports: [SharedModule, AuditTypeRoutingModule],
  declarations: [AuditTypeComponent, AuditTypeDetailComponent, AuditTypeUpdateComponent, AuditTypeDeleteDialogComponent],
  entryComponents: [AuditTypeDeleteDialogComponent],
})
export class AuditTypeModule {}
