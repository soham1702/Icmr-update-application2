import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContactType, ContactType } from '../contact-type.model';
import { ContactTypeService } from '../service/contact-type.service';

@Injectable({ providedIn: 'root' })
export class ContactTypeRoutingResolveService implements Resolve<IContactType> {
  constructor(protected service: ContactTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContactType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((contactType: HttpResponse<ContactType>) => {
          if (contactType.body) {
            return of(contactType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContactType());
  }
}
