import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInventoryType, InventoryType } from '../inventory-type.model';
import { InventoryTypeService } from '../service/inventory-type.service';

@Injectable({ providedIn: 'root' })
export class InventoryTypeRoutingResolveService implements Resolve<IInventoryType> {
  constructor(protected service: InventoryTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInventoryType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((inventoryType: HttpResponse<InventoryType>) => {
          if (inventoryType.body) {
            return of(inventoryType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InventoryType());
  }
}
