import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AuditTypeComponent } from '../list/audit-type.component';
import { AuditTypeDetailComponent } from '../detail/audit-type-detail.component';
import { AuditTypeUpdateComponent } from '../update/audit-type-update.component';
import { AuditTypeRoutingResolveService } from './audit-type-routing-resolve.service';

const auditTypeRoute: Routes = [
  {
    path: '',
    component: AuditTypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AuditTypeDetailComponent,
    resolve: {
      auditType: AuditTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AuditTypeUpdateComponent,
    resolve: {
      auditType: AuditTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AuditTypeUpdateComponent,
    resolve: {
      auditType: AuditTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(auditTypeRoute)],
  exports: [RouterModule],
})
export class AuditTypeRoutingModule {}
