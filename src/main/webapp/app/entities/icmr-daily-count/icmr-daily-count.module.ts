import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ICMRDailyCountComponent } from './list/icmr-daily-count.component';
import { ICMRDailyCountDetailComponent } from './detail/icmr-daily-count-detail.component';
import { ICMRDailyCountUpdateComponent } from './update/icmr-daily-count-update.component';
import { ICMRDailyCountDeleteDialogComponent } from './delete/icmr-daily-count-delete-dialog.component';
import { ICMRDailyCountRoutingModule } from './route/icmr-daily-count-routing.module';

@NgModule({
  imports: [SharedModule, ICMRDailyCountRoutingModule],
  declarations: [
    ICMRDailyCountComponent,
    ICMRDailyCountDetailComponent,
    ICMRDailyCountUpdateComponent,
    ICMRDailyCountDeleteDialogComponent,
  ],
  entryComponents: [ICMRDailyCountDeleteDialogComponent],
})
export class ICMRDailyCountModule {}
