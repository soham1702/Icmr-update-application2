import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHospitalType, HospitalType } from '../hospital-type.model';
import { HospitalTypeService } from '../service/hospital-type.service';

@Injectable({ providedIn: 'root' })
export class HospitalTypeRoutingResolveService implements Resolve<IHospitalType> {
  constructor(protected service: HospitalTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHospitalType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((hospitalType: HttpResponse<HospitalType>) => {
          if (hospitalType.body) {
            return of(hospitalType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HospitalType());
  }
}
