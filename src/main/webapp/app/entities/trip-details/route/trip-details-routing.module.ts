import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TripDetailsComponent } from '../list/trip-details.component';
import { TripDetailsDetailComponent } from '../detail/trip-details-detail.component';
import { TripDetailsUpdateComponent } from '../update/trip-details-update.component';
import { TripDetailsRoutingResolveService } from './trip-details-routing-resolve.service';

const tripDetailsRoute: Routes = [
  {
    path: '',
    component: TripDetailsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TripDetailsDetailComponent,
    resolve: {
      tripDetails: TripDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TripDetailsUpdateComponent,
    resolve: {
      tripDetails: TripDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TripDetailsUpdateComponent,
    resolve: {
      tripDetails: TripDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tripDetailsRoute)],
  exports: [RouterModule],
})
export class TripDetailsRoutingModule {}
