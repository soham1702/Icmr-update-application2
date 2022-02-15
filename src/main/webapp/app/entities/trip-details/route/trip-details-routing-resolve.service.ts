import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITripDetails, TripDetails } from '../trip-details.model';
import { TripDetailsService } from '../service/trip-details.service';

@Injectable({ providedIn: 'root' })
export class TripDetailsRoutingResolveService implements Resolve<ITripDetails> {
  constructor(protected service: TripDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITripDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tripDetails: HttpResponse<TripDetails>) => {
          if (tripDetails.body) {
            return of(tripDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TripDetails());
  }
}
