import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BedTypeComponent } from './list/bed-type.component';
import { BedTypeDetailComponent } from './detail/bed-type-detail.component';
import { BedTypeUpdateComponent } from './update/bed-type-update.component';
import { BedTypeDeleteDialogComponent } from './delete/bed-type-delete-dialog.component';
import { BedTypeRoutingModule } from './route/bed-type-routing.module';

@NgModule({
  imports: [SharedModule, BedTypeRoutingModule],
  declarations: [BedTypeComponent, BedTypeDetailComponent, BedTypeUpdateComponent, BedTypeDeleteDialogComponent],
  entryComponents: [BedTypeDeleteDialogComponent],
})
export class BedTypeModule {}
