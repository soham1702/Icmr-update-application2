import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InventoryUsedComponent } from '../list/inventory-used.component';
import { InventoryUsedDetailComponent } from '../detail/inventory-used-detail.component';
import { InventoryUsedUpdateComponent } from '../update/inventory-used-update.component';
import { InventoryUsedRoutingResolveService } from './inventory-used-routing-resolve.service';

const inventoryUsedRoute: Routes = [
  {
    path: '',
    component: InventoryUsedComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InventoryUsedDetailComponent,
    resolve: {
      inventoryUsed: InventoryUsedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InventoryUsedUpdateComponent,
    resolve: {
      inventoryUsed: InventoryUsedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InventoryUsedUpdateComponent,
    resolve: {
      inventoryUsed: InventoryUsedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(inventoryUsedRoute)],
  exports: [RouterModule],
})
export class InventoryUsedRoutingModule {}
