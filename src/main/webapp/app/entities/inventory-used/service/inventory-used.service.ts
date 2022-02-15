import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInventoryUsed, getInventoryUsedIdentifier } from '../inventory-used.model';

export type EntityResponseType = HttpResponse<IInventoryUsed>;
export type EntityArrayResponseType = HttpResponse<IInventoryUsed[]>;

@Injectable({ providedIn: 'root' })
export class InventoryUsedService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/inventory-useds');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(inventoryUsed: IInventoryUsed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryUsed);
    return this.http
      .post<IInventoryUsed>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inventoryUsed: IInventoryUsed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryUsed);
    return this.http
      .put<IInventoryUsed>(`${this.resourceUrl}/${getInventoryUsedIdentifier(inventoryUsed) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(inventoryUsed: IInventoryUsed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryUsed);
    return this.http
      .patch<IInventoryUsed>(`${this.resourceUrl}/${getInventoryUsedIdentifier(inventoryUsed) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInventoryUsed>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInventoryUsed[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addInventoryUsedToCollectionIfMissing(
    inventoryUsedCollection: IInventoryUsed[],
    ...inventoryUsedsToCheck: (IInventoryUsed | null | undefined)[]
  ): IInventoryUsed[] {
    const inventoryUseds: IInventoryUsed[] = inventoryUsedsToCheck.filter(isPresent);
    if (inventoryUseds.length > 0) {
      const inventoryUsedCollectionIdentifiers = inventoryUsedCollection.map(
        inventoryUsedItem => getInventoryUsedIdentifier(inventoryUsedItem)!
      );
      const inventoryUsedsToAdd = inventoryUseds.filter(inventoryUsedItem => {
        const inventoryUsedIdentifier = getInventoryUsedIdentifier(inventoryUsedItem);
        if (inventoryUsedIdentifier == null || inventoryUsedCollectionIdentifiers.includes(inventoryUsedIdentifier)) {
          return false;
        }
        inventoryUsedCollectionIdentifiers.push(inventoryUsedIdentifier);
        return true;
      });
      return [...inventoryUsedsToAdd, ...inventoryUsedCollection];
    }
    return inventoryUsedCollection;
  }

  protected convertDateFromClient(inventoryUsed: IInventoryUsed): IInventoryUsed {
    return Object.assign({}, inventoryUsed, {
      lastModified: inventoryUsed.lastModified?.isValid() ? inventoryUsed.lastModified.toJSON() : undefined,
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
      res.body.forEach((inventoryUsed: IInventoryUsed) => {
        inventoryUsed.lastModified = inventoryUsed.lastModified ? dayjs(inventoryUsed.lastModified) : undefined;
      });
    }
    return res;
  }
}
