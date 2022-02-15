import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BedInventoryComponent } from '../list/bed-inventory.component';
import { BedInventoryDetailComponent } from '../detail/bed-inventory-detail.component';
import { BedInventoryUpdateComponent } from '../update/bed-inventory-update.component';
import { BedInventoryRoutingResolveService } from './bed-inventory-routing-resolve.service';

const bedInventoryRoute: Routes = [
  {
    path: '',
    component: BedInventoryComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BedInventoryDetailComponent,
    resolve: {
      bedInventory: BedInventoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BedInventoryUpdateComponent,
    resolve: {
      bedInventory: BedInventoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BedInventoryUpdateComponent,
    resolve: {
      bedInventory: BedInventoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bedInventoryRoute)],
  exports: [RouterModule],
})
export class BedInventoryRoutingModule {}
