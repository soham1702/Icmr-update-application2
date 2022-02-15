import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InventoryUsedComponent } from './list/inventory-used.component';
import { InventoryUsedDetailComponent } from './detail/inventory-used-detail.component';
import { InventoryUsedUpdateComponent } from './update/inventory-used-update.component';
import { InventoryUsedDeleteDialogComponent } from './delete/inventory-used-delete-dialog.component';
import { InventoryUsedRoutingModule } from './route/inventory-used-routing.module';

@NgModule({
  imports: [SharedModule, InventoryUsedRoutingModule],
  declarations: [InventoryUsedComponent, InventoryUsedDetailComponent, InventoryUsedUpdateComponent, InventoryUsedDeleteDialogComponent],
  entryComponents: [InventoryUsedDeleteDialogComponent],
})
export class InventoryUsedModule {}
