import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MunicipalCorpComponent } from '../list/municipal-corp.component';
import { MunicipalCorpDetailComponent } from '../detail/municipal-corp-detail.component';
import { MunicipalCorpUpdateComponent } from '../update/municipal-corp-update.component';
import { MunicipalCorpRoutingResolveService } from './municipal-corp-routing-resolve.service';

const municipalCorpRoute: Routes = [
  {
    path: '',
    component: MunicipalCorpComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MunicipalCorpDetailComponent,
    resolve: {
      municipalCorp: MunicipalCorpRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MunicipalCorpUpdateComponent,
    resolve: {
      municipalCorp: MunicipalCorpRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MunicipalCorpUpdateComponent,
    resolve: {
      municipalCorp: MunicipalCorpRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(municipalCorpRoute)],
  exports: [RouterModule],
})
export class MunicipalCorpRoutingModule {}
