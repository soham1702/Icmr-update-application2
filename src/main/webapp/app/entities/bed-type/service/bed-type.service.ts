import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBedType, getBedTypeIdentifier } from '../bed-type.model';

export type EntityResponseType = HttpResponse<IBedType>;
export type EntityArrayResponseType = HttpResponse<IBedType[]>;

@Injectable({ providedIn: 'root' })
export class BedTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bed-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bedType: IBedType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bedType);
    return this.http
      .post<IBedType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bedType: IBedType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bedType);
    return this.http
      .put<IBedType>(`${this.resourceUrl}/${getBedTypeIdentifier(bedType) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(bedType: IBedType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bedType);
    return this.http
      .patch<IBedType>(`${this.resourceUrl}/${getBedTypeIdentifier(bedType) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBedType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBedType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBedTypeToCollectionIfMissing(bedTypeCollection: IBedType[], ...bedTypesToCheck: (IBedType | null | undefined)[]): IBedType[] {
    const bedTypes: IBedType[] = bedTypesToCheck.filter(isPresent);
    if (bedTypes.length > 0) {
      const bedTypeCollectionIdentifiers = bedTypeCollection.map(bedTypeItem => getBedTypeIdentifier(bedTypeItem)!);
      const bedTypesToAdd = bedTypes.filter(bedTypeItem => {
        const bedTypeIdentifier = getBedTypeIdentifier(bedTypeItem);
        if (bedTypeIdentifier == null || bedTypeCollectionIdentifiers.includes(bedTypeIdentifier)) {
          return false;
        }
        bedTypeCollectionIdentifiers.push(bedTypeIdentifier);
        return true;
      });
      return [...bedTypesToAdd, ...bedTypeCollection];
    }
    return bedTypeCollection;
  }

  protected convertDateFromClient(bedType: IBedType): IBedType {
    return Object.assign({}, bedType, {
      lastModified: bedType.lastModified?.isValid() ? bedType.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((bedType: IBedType) => {
        bedType.lastModified = bedType.lastModified ? dayjs(bedType.lastModified) : undefined;
      });
    }
    return res;
  }
}
