import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TalukaComponent } from '../list/taluka.component';
import { TalukaDetailComponent } from '../detail/taluka-detail.component';
import { TalukaUpdateComponent } from '../update/taluka-update.component';
import { TalukaRoutingResolveService } from './taluka-routing-resolve.service';

const talukaRoute: Routes = [
  {
    path: '',
    component: TalukaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TalukaDetailComponent,
    resolve: {
      taluka: TalukaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TalukaUpdateComponent,
    resolve: {
      taluka: TalukaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TalukaUpdateComponent,
    resolve: {
      taluka: TalukaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(talukaRoute)],
  exports: [RouterModule],
})
export class TalukaRoutingModule {}
