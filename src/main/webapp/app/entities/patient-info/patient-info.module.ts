import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PatientInfoComponent } from './list/patient-info.component';
import { PatientInfoDetailComponent } from './detail/patient-info-detail.component';
import { PatientInfoUpdateComponent } from './update/patient-info-update.component';
import { PatientInfoDeleteDialogComponent } from './delete/patient-info-delete-dialog.component';
import { PatientInfoRoutingModule } from './route/patient-info-routing.module';

@NgModule({
  imports: [SharedModule, PatientInfoRoutingModule],
  declarations: [PatientInfoComponent, PatientInfoDetailComponent, PatientInfoUpdateComponent, PatientInfoDeleteDialogComponent],
  entryComponents: [PatientInfoDeleteDialogComponent],
})
export class PatientInfoModule {}
