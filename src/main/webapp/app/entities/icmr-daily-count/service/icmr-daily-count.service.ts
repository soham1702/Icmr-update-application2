import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IICMRDailyCount, getICMRDailyCountIdentifier } from '../icmr-daily-count.model';

export type EntityResponseType = HttpResponse<IICMRDailyCount>;
export type EntityArrayResponseType = HttpResponse<IICMRDailyCount[]>;

@Injectable({ providedIn: 'root' })
export class ICMRDailyCountService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/icmr-daily-counts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(iCMRDailyCount: IICMRDailyCount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(iCMRDailyCount);
    return this.http
      .post<IICMRDailyCount>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(iCMRDailyCount: IICMRDailyCount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(iCMRDailyCount);
    return this.http
      .put<IICMRDailyCount>(`${this.resourceUrl}/${getICMRDailyCountIdentifier(iCMRDailyCount) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(iCMRDailyCount: IICMRDailyCount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(iCMRDailyCount);
    return this.http
      .patch<IICMRDailyCount>(`${this.resourceUrl}/${getICMRDailyCountIdentifier(iCMRDailyCount) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IICMRDailyCount>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IICMRDailyCount[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addICMRDailyCountToCollectionIfMissing(
    iCMRDailyCountCollection: IICMRDailyCount[],
    ...iCMRDailyCountsToCheck: (IICMRDailyCount | null | undefined)[]
  ): IICMRDailyCount[] {
    const iCMRDailyCounts: IICMRDailyCount[] = iCMRDailyCountsToCheck.filter(isPresent);
    if (iCMRDailyCounts.length > 0) {
      const iCMRDailyCountCollectionIdentifiers = iCMRDailyCountCollection.map(
        iCMRDailyCountItem => getICMRDailyCountIdentifier(iCMRDailyCountItem)!
      );
      const iCMRDailyCountsToAdd = iCMRDailyCounts.filter(iCMRDailyCountItem => {
        const iCMRDailyCountIdentifier = getICMRDailyCountIdentifier(iCMRDailyCountItem);
        if (iCMRDailyCountIdentifier == null || iCMRDailyCountCollectionIdentifiers.includes(iCMRDailyCountIdentifier)) {
          return false;
        }
        iCMRDailyCountCollectionIdentifiers.push(iCMRDailyCountIdentifier);
        return true;
      });
      return [...iCMRDailyCountsToAdd, ...iCMRDailyCountCollection];
    }
    return iCMRDailyCountCollection;
  }

  protected convertDateFromClient(iCMRDailyCount: IICMRDailyCount): IICMRDailyCount {
    return Object.assign({}, iCMRDailyCount, {
      editedOn: iCMRDailyCount.editedOn?.isValid() ? iCMRDailyCount.editedOn.toJSON() : undefined,
      updatedDate: iCMRDailyCount.updatedDate?.isValid() ? iCMRDailyCount.updatedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.editedOn = res.body.editedOn ? dayjs(res.body.editedOn) : undefined;
      res.body.updatedDate = res.body.updatedDate ? dayjs(res.body.updatedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((iCMRDailyCount: IICMRDailyCount) => {
        iCMRDailyCount.editedOn = iCMRDailyCount.editedOn ? dayjs(iCMRDailyCount.editedOn) : undefined;
        iCMRDailyCount.updatedDate = iCMRDailyCount.updatedDate ? dayjs(iCMRDailyCount.updatedDate) : undefined;
      });
    }
    return res;
  }
}
