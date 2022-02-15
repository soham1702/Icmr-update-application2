import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInventoryMaster, InventoryMaster } from '../inventory-master.model';
import { InventoryMasterService } from '../service/inventory-master.service';

@Injectable({ providedIn: 'root' })
export class InventoryMasterRoutingResolveService implements Resolve<IInventoryMaster> {
  constructor(protected service: InventoryMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInventoryMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((inventoryMaster: HttpResponse<InventoryMaster>) => {
          if (inventoryMaster.body) {
            return of(inventoryMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InventoryMaster());
  }
}
