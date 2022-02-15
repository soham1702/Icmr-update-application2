import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInventoryUsed, InventoryUsed } from '../inventory-used.model';
import { InventoryUsedService } from '../service/inventory-used.service';

@Injectable({ providedIn: 'root' })
export class InventoryUsedRoutingResolveService implements Resolve<IInventoryUsed> {
  constructor(protected service: InventoryUsedService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInventoryUsed> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((inventoryUsed: HttpResponse<InventoryUsed>) => {
          if (inventoryUsed.body) {
            return of(inventoryUsed.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InventoryUsed());
  }
}
