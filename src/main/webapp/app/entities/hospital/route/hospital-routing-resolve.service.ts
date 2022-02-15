import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHospital, Hospital } from '../hospital.model';
import { HospitalService } from '../service/hospital.service';

@Injectable({ providedIn: 'root' })
export class HospitalRoutingResolveService implements Resolve<IHospital> {
  constructor(protected service: HospitalService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHospital> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((hospital: HttpResponse<Hospital>) => {
          if (hospital.body) {
            return of(hospital.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Hospital());
  }
}
