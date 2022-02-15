import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBedType, BedType } from '../bed-type.model';
import { BedTypeService } from '../service/bed-type.service';

@Injectable({ providedIn: 'root' })
export class BedTypeRoutingResolveService implements Resolve<IBedType> {
  constructor(protected service: BedTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBedType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bedType: HttpResponse<BedType>) => {
          if (bedType.body) {
            return of(bedType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BedType());
  }
}
