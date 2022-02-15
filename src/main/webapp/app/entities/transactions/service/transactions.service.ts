import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITransactions, getTransactionsIdentifier } from '../transactions.model';

export type EntityResponseType = HttpResponse<ITransactions>;
export type EntityArrayResponseType = HttpResponse<ITransactions[]>;

@Injectable({ providedIn: 'root' })
export class TransactionsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/transactions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(transactions: ITransactions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactions);
    return this.http
      .post<ITransactions>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transactions: ITransactions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactions);
    return this.http
      .put<ITransactions>(`${this.resourceUrl}/${getTransactionsIdentifier(transactions) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(transactions: ITransactions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactions);
    return this.http
      .patch<ITransactions>(`${this.resourceUrl}/${getTransactionsIdentifier(transactions) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransactions>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransactions[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTransactionsToCollectionIfMissing(
    transactionsCollection: ITransactions[],
    ...transactionsToCheck: (ITransactions | null | undefined)[]
  ): ITransactions[] {
    const transactions: ITransactions[] = transactionsToCheck.filter(isPresent);
    if (transactions.length > 0) {
      const transactionsCollectionIdentifiers = transactionsCollection.map(
        transactionsItem => getTransactionsIdentifier(transactionsItem)!
      );
      const transactionsToAdd = transactions.filter(transactionsItem => {
        const transactionsIdentifier = getTransactionsIdentifier(transactionsItem);
        if (transactionsIdentifier == null || transactionsCollectionIdentifiers.includes(transactionsIdentifier)) {
          return false;
        }
        transactionsCollectionIdentifiers.push(transactionsIdentifier);
        return true;
      });
      return [...transactionsToAdd, ...transactionsCollection];
    }
    return transactionsCollection;
  }

  protected convertDateFromClient(transactions: ITransactions): ITransactions {
    return Object.assign({}, transactions, {
      lastModified: transactions.lastModified?.isValid() ? transactions.lastModified.toJSON() : undefined,
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
      res.body.forEach((transactions: ITransactions) => {
        transactions.lastModified = transactions.lastModified ? dayjs(transactions.lastModified) : undefined;
      });
    }
    return res;
  }
}
