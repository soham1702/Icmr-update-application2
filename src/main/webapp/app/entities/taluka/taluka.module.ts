import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TalukaComponent } from './list/taluka.component';
import { TalukaDetailComponent } from './detail/taluka-detail.component';
import { TalukaUpdateComponent } from './update/taluka-update.component';
import { TalukaDeleteDialogComponent } from './delete/taluka-delete-dialog.component';
import { TalukaRoutingModule } from './route/taluka-routing.module';

@NgModule({
  imports: [SharedModule, TalukaRoutingModule],
  declarations: [TalukaComponent, TalukaDetailComponent, TalukaUpdateComponent, TalukaDeleteDialogComponent],
  entryComponents: [TalukaDeleteDialogComponent],
})
export class TalukaModule {}
