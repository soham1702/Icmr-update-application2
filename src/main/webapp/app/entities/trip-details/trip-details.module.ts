import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TripDetailsComponent } from './list/trip-details.component';
import { TripDetailsDetailComponent } from './detail/trip-details-detail.component';
import { TripDetailsUpdateComponent } from './update/trip-details-update.component';
import { TripDetailsDeleteDialogComponent } from './delete/trip-details-delete-dialog.component';
import { TripDetailsRoutingModule } from './route/trip-details-routing.module';

@NgModule({
  imports: [SharedModule, TripDetailsRoutingModule],
  declarations: [TripDetailsComponent, TripDetailsDetailComponent, TripDetailsUpdateComponent, TripDetailsDeleteDialogComponent],
  entryComponents: [TripDetailsDeleteDialogComponent],
})
export class TripDetailsModule {}
