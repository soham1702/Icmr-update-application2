import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TransactionsComponent } from '../list/transactions.component';
import { TransactionsDetailComponent } from '../detail/transactions-detail.component';
import { TransactionsUpdateComponent } from '../update/transactions-update.component';
import { TransactionsRoutingResolveService } from './transactions-routing-resolve.service';

const transactionsRoute: Routes = [
  {
    path: '',
    component: TransactionsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransactionsDetailComponent,
    resolve: {
      transactions: TransactionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransactionsUpdateComponent,
    resolve: {
      transactions: TransactionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransactionsUpdateComponent,
    resolve: {
      transactions: TransactionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(transactionsRoute)],
  exports: [RouterModule],
})
export class TransactionsRoutingModule {}
