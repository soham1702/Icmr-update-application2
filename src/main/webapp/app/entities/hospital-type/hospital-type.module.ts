import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HospitalTypeComponent } from './list/hospital-type.component';
import { HospitalTypeDetailComponent } from './detail/hospital-type-detail.component';
import { HospitalTypeUpdateComponent } from './update/hospital-type-update.component';
import { HospitalTypeDeleteDialogComponent } from './delete/hospital-type-delete-dialog.component';
import { HospitalTypeRoutingModule } from './route/hospital-type-routing.module';

@NgModule({
  imports: [SharedModule, HospitalTypeRoutingModule],
  declarations: [HospitalTypeComponent, HospitalTypeDetailComponent, HospitalTypeUpdateComponent, HospitalTypeDeleteDialogComponent],
  entryComponents: [HospitalTypeDeleteDialogComponent],
})
export class HospitalTypeModule {}
