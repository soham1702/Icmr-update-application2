import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ICMRDailyCountComponent } from '../list/icmr-daily-count.component';
import { ICMRDailyCountDetailComponent } from '../detail/icmr-daily-count-detail.component';
import { ICMRDailyCountUpdateComponent } from '../update/icmr-daily-count-update.component';
import { ICMRDailyCountRoutingResolveService } from './icmr-daily-count-routing-resolve.service';

const iCMRDailyCountRoute: Routes = [
  {
    path: '',
    component: ICMRDailyCountComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ICMRDailyCountDetailComponent,
    resolve: {
      iCMRDailyCount: ICMRDailyCountRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ICMRDailyCountUpdateComponent,
    resolve: {
      iCMRDailyCount: ICMRDailyCountRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ICMRDailyCountUpdateComponent,
    resolve: {
      iCMRDailyCount: ICMRDailyCountRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(iCMRDailyCountRoute)],
  exports: [RouterModule],
})
export class ICMRDailyCountRoutingModule {}
