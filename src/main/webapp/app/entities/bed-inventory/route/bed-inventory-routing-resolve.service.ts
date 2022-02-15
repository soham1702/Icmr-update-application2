import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBedInventory, BedInventory } from '../bed-inventory.model';
import { BedInventoryService } from '../service/bed-inventory.service';

@Injectable({ providedIn: 'root' })
export class BedInventoryRoutingResolveService implements Resolve<IBedInventory> {
  constructor(protected service: BedInventoryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBedInventory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bedInventory: HttpResponse<BedInventory>) => {
          if (bedInventory.body) {
            return of(bedInventory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BedInventory());
  }
}
