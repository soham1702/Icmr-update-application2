import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ContactTypeComponent } from '../list/contact-type.component';
import { ContactTypeDetailComponent } from '../detail/contact-type-detail.component';
import { ContactTypeUpdateComponent } from '../update/contact-type-update.component';
import { ContactTypeRoutingResolveService } from './contact-type-routing-resolve.service';

const contactTypeRoute: Routes = [
  {
    path: '',
    component: ContactTypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContactTypeDetailComponent,
    resolve: {
      contactType: ContactTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContactTypeUpdateComponent,
    resolve: {
      contactType: ContactTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContactTypeUpdateComponent,
    resolve: {
      contactType: ContactTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(contactTypeRoute)],
  exports: [RouterModule],
})
export class ContactTypeRoutingModule {}
