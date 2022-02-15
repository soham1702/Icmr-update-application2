import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BedTransactionsComponent } from '../list/bed-transactions.component';
import { BedTransactionsDetailComponent } from '../detail/bed-transactions-detail.component';
import { BedTransactionsUpdateComponent } from '../update/bed-transactions-update.component';
import { BedTransactionsRoutingResolveService } from './bed-transactions-routing-resolve.service';

const bedTransactionsRoute: Routes = [
  {
    path: '',
    component: BedTransactionsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BedTransactionsDetailComponent,
    resolve: {
      bedTransactions: BedTransactionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BedTransactionsUpdateComponent,
    resolve: {
      bedTransactions: BedTransactionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BedTransactionsUpdateComponent,
    resolve: {
      bedTransactions: BedTransactionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bedTransactionsRoute)],
  exports: [RouterModule],
})
export class BedTransactionsRoutingModule {}
