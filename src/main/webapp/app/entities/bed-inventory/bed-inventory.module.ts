import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BedInventoryComponent } from './list/bed-inventory.component';
import { BedInventoryDetailComponent } from './detail/bed-inventory-detail.component';
import { BedInventoryUpdateComponent } from './update/bed-inventory-update.component';
import { BedInventoryDeleteDialogComponent } from './delete/bed-inventory-delete-dialog.component';
import { BedInventoryRoutingModule } from './route/bed-inventory-routing.module';

@NgModule({
  imports: [SharedModule, BedInventoryRoutingModule],
  declarations: [BedInventoryComponent, BedInventoryDetailComponent, BedInventoryUpdateComponent, BedInventoryDeleteDialogComponent],
  entryComponents: [BedInventoryDeleteDialogComponent],
})
export class BedInventoryModule {}
