import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAuditSystem, AuditSystem } from '../audit-system.model';
import { AuditSystemService } from '../service/audit-system.service';

@Injectable({ providedIn: 'root' })
export class AuditSystemRoutingResolveService implements Resolve<IAuditSystem> {
  constructor(protected service: AuditSystemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAuditSystem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((auditSystem: HttpResponse<AuditSystem>) => {
          if (auditSystem.body) {
            return of(auditSystem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AuditSystem());
  }
}
