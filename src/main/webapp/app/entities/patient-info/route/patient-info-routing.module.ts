import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PatientInfoComponent } from '../list/patient-info.component';
import { PatientInfoDetailComponent } from '../detail/patient-info-detail.component';
import { PatientInfoUpdateComponent } from '../update/patient-info-update.component';
import { PatientInfoRoutingResolveService } from './patient-info-routing-resolve.service';

const patientInfoRoute: Routes = [
  {
    path: '',
    component: PatientInfoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PatientInfoDetailComponent,
    resolve: {
      patientInfo: PatientInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PatientInfoUpdateComponent,
    resolve: {
      patientInfo: PatientInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PatientInfoUpdateComponent,
    resolve: {
      patientInfo: PatientInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(patientInfoRoute)],
  exports: [RouterModule],
})
export class PatientInfoRoutingModule {}
