import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPatientInfo, PatientInfo } from '../patient-info.model';
import { PatientInfoService } from '../service/patient-info.service';

@Injectable({ providedIn: 'root' })
export class PatientInfoRoutingResolveService implements Resolve<IPatientInfo> {
  constructor(protected service: PatientInfoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPatientInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((patientInfo: HttpResponse<PatientInfo>) => {
          if (patientInfo.body) {
            return of(patientInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PatientInfo());
  }
}
