import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IICMRDailyCount, ICMRDailyCount } from '../icmr-daily-count.model';
import { ICMRDailyCountService } from '../service/icmr-daily-count.service';

@Injectable({ providedIn: 'root' })
export class ICMRDailyCountRoutingResolveService implements Resolve<IICMRDailyCount> {
  constructor(protected service: ICMRDailyCountService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IICMRDailyCount> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((iCMRDailyCount: HttpResponse<ICMRDailyCount>) => {
          if (iCMRDailyCount.body) {
            return of(iCMRDailyCount.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ICMRDailyCount());
  }
}
