import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BedTypeComponent } from '../list/bed-type.component';
import { BedTypeDetailComponent } from '../detail/bed-type-detail.component';
import { BedTypeUpdateComponent } from '../update/bed-type-update.component';
import { BedTypeRoutingResolveService } from './bed-type-routing-resolve.service';

const bedTypeRoute: Routes = [
  {
    path: '',
    component: BedTypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BedTypeDetailComponent,
    resolve: {
      bedType: BedTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BedTypeUpdateComponent,
    resolve: {
      bedType: BedTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BedTypeUpdateComponent,
    resolve: {
      bedType: BedTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bedTypeRoute)],
  exports: [RouterModule],
})
export class BedTypeRoutingModule {}
