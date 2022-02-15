import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInventoryType, getInventoryTypeIdentifier } from '../inventory-type.model';

export type EntityResponseType = HttpResponse<IInventoryType>;
export type EntityArrayResponseType = HttpResponse<IInventoryType[]>;

@Injectable({ providedIn: 'root' })
export class InventoryTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/inventory-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(inventoryType: IInventoryType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryType);
    return this.http
      .post<IInventoryType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inventoryType: IInventoryType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryType);
    return this.http
      .put<IInventoryType>(`${this.resourceUrl}/${getInventoryTypeIdentifier(inventoryType) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(inventoryType: IInventoryType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryType);
    return this.http
      .patch<IInventoryType>(`${this.resourceUrl}/${getInventoryTypeIdentifier(inventoryType) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInventoryType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInventoryType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addInventoryTypeToCollectionIfMissing(
    inventoryTypeCollection: IInventoryType[],
    ...inventoryTypesToCheck: (IInventoryType | null | undefined)[]
  ): IInventoryType[] {
    const inventoryTypes: IInventoryType[] = inventoryTypesToCheck.filter(isPresent);
    if (inventoryTypes.length > 0) {
      const inventoryTypeCollectionIdentifiers = inventoryTypeCollection.map(
        inventoryTypeItem => getInventoryTypeIdentifier(inventoryTypeItem)!
      );
      const inventoryTypesToAdd = inventoryTypes.filter(inventoryTypeItem => {
        const inventoryTypeIdentifier = getInventoryTypeIdentifier(inventoryTypeItem);
        if (inventoryTypeIdentifier == null || inventoryTypeCollectionIdentifiers.includes(inventoryTypeIdentifier)) {
          return false;
        }
        inventoryTypeCollectionIdentifiers.push(inventoryTypeIdentifier);
        return true;
      });
      return [...inventoryTypesToAdd, ...inventoryTypeCollection];
    }
    return inventoryTypeCollection;
  }

  protected convertDateFromClient(inventoryType: IInventoryType): IInventoryType {
    return Object.assign({}, inventoryType, {
      lastModified: inventoryType.lastModified?.isValid() ? inventoryType.lastModified.toJSON() : undefined,
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
      res.body.forEach((inventoryType: IInventoryType) => {
        inventoryType.lastModified = inventoryType.lastModified ? dayjs(inventoryType.lastModified) : undefined;
      });
    }
    return res;
  }
}
