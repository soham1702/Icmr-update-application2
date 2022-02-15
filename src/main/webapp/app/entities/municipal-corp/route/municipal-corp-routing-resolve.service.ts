import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMunicipalCorp, MunicipalCorp } from '../municipal-corp.model';
import { MunicipalCorpService } from '../service/municipal-corp.service';

@Injectable({ providedIn: 'root' })
export class MunicipalCorpRoutingResolveService implements Resolve<IMunicipalCorp> {
  constructor(protected service: MunicipalCorpService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMunicipalCorp> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((municipalCorp: HttpResponse<MunicipalCorp>) => {
          if (municipalCorp.body) {
            return of(municipalCorp.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MunicipalCorp());
  }
}
