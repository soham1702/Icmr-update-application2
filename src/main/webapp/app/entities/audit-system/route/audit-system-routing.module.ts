import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AuditSystemComponent } from '../list/audit-system.component';
import { AuditSystemDetailComponent } from '../detail/audit-system-detail.component';
import { AuditSystemUpdateComponent } from '../update/audit-system-update.component';
import { AuditSystemRoutingResolveService } from './audit-system-routing-resolve.service';

const auditSystemRoute: Routes = [
  {
    path: '',
    component: AuditSystemComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AuditSystemDetailComponent,
    resolve: {
      auditSystem: AuditSystemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AuditSystemUpdateComponent,
    resolve: {
      auditSystem: AuditSystemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AuditSystemUpdateComponent,
    resolve: {
      auditSystem: AuditSystemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(auditSystemRoute)],
  exports: [RouterModule],
})
export class AuditSystemRoutingModule {}
