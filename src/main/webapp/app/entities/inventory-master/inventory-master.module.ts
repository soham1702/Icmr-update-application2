import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InventoryMasterComponent } from './list/inventory-master.component';
import { InventoryMasterDetailComponent } from './detail/inventory-master-detail.component';
import { InventoryMasterUpdateComponent } from './update/inventory-master-update.component';
import { InventoryMasterDeleteDialogComponent } from './delete/inventory-master-delete-dialog.component';
import { InventoryMasterRoutingModule } from './route/inventory-master-routing.module';

@NgModule({
  imports: [SharedModule, InventoryMasterRoutingModule],
  declarations: [
    InventoryMasterComponent,
    InventoryMasterDetailComponent,
    InventoryMasterUpdateComponent,
    InventoryMasterDeleteDialogComponent,
  ],
  entryComponents: [InventoryMasterDeleteDialogComponent],
})
export class InventoryMasterModule {}
