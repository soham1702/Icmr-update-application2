import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBedTransactions, getBedTransactionsIdentifier } from '../bed-transactions.model';

export type EntityResponseType = HttpResponse<IBedTransactions>;
export type EntityArrayResponseType = HttpResponse<IBedTransactions[]>;

@Injectable({ providedIn: 'root' })
export class BedTransactionsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bed-transactions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bedTransactions: IBedTransactions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bedTransactions);
    return this.http
      .post<IBedTransactions>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bedTransactions: IBedTransactions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bedTransactions);
    return this.http
      .put<IBedTransactions>(`${this.resourceUrl}/${getBedTransactionsIdentifier(bedTransactions) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(bedTransactions: IBedTransactions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bedTransactions);
    return this.http
      .patch<IBedTransactions>(`${this.resourceUrl}/${getBedTransactionsIdentifier(bedTransactions) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBedTransactions>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBedTransactions[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBedTransactionsToCollectionIfMissing(
    bedTransactionsCollection: IBedTransactions[],
    ...bedTransactionsToCheck: (IBedTransactions | null | undefined)[]
  ): IBedTransactions[] {
    const bedTransactions: IBedTransactions[] = bedTransactionsToCheck.filter(isPresent);
    if (bedTransactions.length > 0) {
      const bedTransactionsCollectionIdentifiers = bedTransactionsCollection.map(
        bedTransactionsItem => getBedTransactionsIdentifier(bedTransactionsItem)!
      );
      const bedTransactionsToAdd = bedTransactions.filter(bedTransactionsItem => {
        const bedTransactionsIdentifier = getBedTransactionsIdentifier(bedTransactionsItem);
        if (bedTransactionsIdentifier == null || bedTransactionsCollectionIdentifiers.includes(bedTransactionsIdentifier)) {
          return false;
        }
        bedTransactionsCollectionIdentifiers.push(bedTransactionsIdentifier);
        return true;
      });
      return [...bedTransactionsToAdd, ...bedTransactionsCollection];
    }
    return bedTransactionsCollection;
  }

  protected convertDateFromClient(bedTransactions: IBedTransactions): IBedTransactions {
    return Object.assign({}, bedTransactions, {
      lastModified: bedTransactions.lastModified?.isValid() ? bedTransactions.lastModified.toJSON() : undefined,
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
      res.body.forEach((bedTransactions: IBedTransactions) => {
        bedTransactions.lastModified = bedTransactions.lastModified ? dayjs(bedTransactions.lastModified) : undefined;
      });
    }
    return res;
  }
}
