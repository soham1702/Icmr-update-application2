import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InventoryMasterComponent } from '../list/inventory-master.component';
import { InventoryMasterDetailComponent } from '../detail/inventory-master-detail.component';
import { InventoryMasterUpdateComponent } from '../update/inventory-master-update.component';
import { InventoryMasterRoutingResolveService } from './inventory-master-routing-resolve.service';

const inventoryMasterRoute: Routes = [
  {
    path: '',
    component: InventoryMasterComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InventoryMasterDetailComponent,
    resolve: {
      inventoryMaster: InventoryMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InventoryMasterUpdateComponent,
    resolve: {
      inventoryMaster: InventoryMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InventoryMasterUpdateComponent,
    resolve: {
      inventoryMaster: InventoryMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(inventoryMasterRoute)],
  exports: [RouterModule],
})
export class InventoryMasterRoutingModule {}
