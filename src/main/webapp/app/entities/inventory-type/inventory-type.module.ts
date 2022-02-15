import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InventoryTypeComponent } from './list/inventory-type.component';
import { InventoryTypeDetailComponent } from './detail/inventory-type-detail.component';
import { InventoryTypeUpdateComponent } from './update/inventory-type-update.component';
import { InventoryTypeDeleteDialogComponent } from './delete/inventory-type-delete-dialog.component';
import { InventoryTypeRoutingModule } from './route/inventory-type-routing.module';

@NgModule({
  imports: [SharedModule, InventoryTypeRoutingModule],
  declarations: [InventoryTypeComponent, InventoryTypeDetailComponent, InventoryTypeUpdateComponent, InventoryTypeDeleteDialogComponent],
  entryComponents: [InventoryTypeDeleteDialogComponent],
})
export class InventoryTypeModule {}
