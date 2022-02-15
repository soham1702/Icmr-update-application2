import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InventoryTypeComponent } from '../list/inventory-type.component';
import { InventoryTypeDetailComponent } from '../detail/inventory-type-detail.component';
import { InventoryTypeUpdateComponent } from '../update/inventory-type-update.component';
import { InventoryTypeRoutingResolveService } from './inventory-type-routing-resolve.service';

const inventoryTypeRoute: Routes = [
  {
    path: '',
    component: InventoryTypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InventoryTypeDetailComponent,
    resolve: {
      inventoryType: InventoryTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InventoryTypeUpdateComponent,
    resolve: {
      inventoryType: InventoryTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InventoryTypeUpdateComponent,
    resolve: {
      inventoryType: InventoryTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(inventoryTypeRoute)],
  exports: [RouterModule],
})
export class InventoryTypeRoutingModule {}
