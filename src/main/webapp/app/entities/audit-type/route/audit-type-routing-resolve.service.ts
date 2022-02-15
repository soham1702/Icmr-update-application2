import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAuditType, AuditType } from '../audit-type.model';
import { AuditTypeService } from '../service/audit-type.service';

@Injectable({ providedIn: 'root' })
export class AuditTypeRoutingResolveService implements Resolve<IAuditType> {
  constructor(protected service: AuditTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAuditType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((auditType: HttpResponse<AuditType>) => {
          if (auditType.body) {
            return of(auditType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AuditType());
  }
}
