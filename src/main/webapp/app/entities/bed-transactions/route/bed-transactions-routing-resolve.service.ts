import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBedTransactions, BedTransactions } from '../bed-transactions.model';
import { BedTransactionsService } from '../service/bed-transactions.service';

@Injectable({ providedIn: 'root' })
export class BedTransactionsRoutingResolveService implements Resolve<IBedTransactions> {
  constructor(protected service: BedTransactionsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBedTransactions> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bedTransactions: HttpResponse<BedTransactions>) => {
          if (bedTransactions.body) {
            return of(bedTransactions.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BedTransactions());
  }
}
