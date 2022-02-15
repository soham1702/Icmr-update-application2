import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITripDetails, getTripDetailsIdentifier } from '../trip-details.model';

export type EntityResponseType = HttpResponse<ITripDetails>;
export type EntityArrayResponseType = HttpResponse<ITripDetails[]>;

@Injectable({ providedIn: 'root' })
export class TripDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/trip-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tripDetails: ITripDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tripDetails);
    return this.http
      .post<ITripDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tripDetails: ITripDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tripDetails);
    return this.http
      .put<ITripDetails>(`${this.resourceUrl}/${getTripDetailsIdentifier(tripDetails) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tripDetails: ITripDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tripDetails);
    return this.http
      .patch<ITripDetails>(`${this.resourceUrl}/${getTripDetailsIdentifier(tripDetails) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITripDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITripDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTripDetailsToCollectionIfMissing(
    tripDetailsCollection: ITripDetails[],
    ...tripDetailsToCheck: (ITripDetails | null | undefined)[]
  ): ITripDetails[] {
    const tripDetails: ITripDetails[] = tripDetailsToCheck.filter(isPresent);
    if (tripDetails.length > 0) {
      const tripDetailsCollectionIdentifiers = tripDetailsCollection.map(tripDetailsItem => getTripDetailsIdentifier(tripDetailsItem)!);
      const tripDetailsToAdd = tripDetails.filter(tripDetailsItem => {
        const tripDetailsIdentifier = getTripDetailsIdentifier(tripDetailsItem);
        if (tripDetailsIdentifier == null || tripDetailsCollectionIdentifiers.includes(tripDetailsIdentifier)) {
          return false;
        }
        tripDetailsCollectionIdentifiers.push(tripDetailsIdentifier);
        return true;
      });
      return [...tripDetailsToAdd, ...tripDetailsCollection];
    }
    return tripDetailsCollection;
  }

  protected convertDateFromClient(tripDetails: ITripDetails): ITripDetails {
    return Object.assign({}, tripDetails, {
      createdDate: tripDetails.createdDate?.isValid() ? tripDetails.createdDate.toJSON() : undefined,
      lastModified: tripDetails.lastModified?.isValid() ? tripDetails.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tripDetails: ITripDetails) => {
        tripDetails.createdDate = tripDetails.createdDate ? dayjs(tripDetails.createdDate) : undefined;
        tripDetails.lastModified = tripDetails.lastModified ? dayjs(tripDetails.lastModified) : undefined;
      });
    }
    return res;
  }
}
