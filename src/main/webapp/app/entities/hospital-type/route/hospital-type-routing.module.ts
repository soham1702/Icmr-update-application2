import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HospitalTypeComponent } from '../list/hospital-type.component';
import { HospitalTypeDetailComponent } from '../detail/hospital-type-detail.component';
import { HospitalTypeUpdateComponent } from '../update/hospital-type-update.component';
import { HospitalTypeRoutingResolveService } from './hospital-type-routing-resolve.service';

const hospitalTypeRoute: Routes = [
  {
    path: '',
    component: HospitalTypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HospitalTypeDetailComponent,
    resolve: {
      hospitalType: HospitalTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HospitalTypeUpdateComponent,
    resolve: {
      hospitalType: HospitalTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HospitalTypeUpdateComponent,
    resolve: {
      hospitalType: HospitalTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(hospitalTypeRoute)],
  exports: [RouterModule],
})
export class HospitalTypeRoutingModule {}
