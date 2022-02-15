import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInventory, getInventoryIdentifier } from '../inventory.model';

export type EntityResponseType = HttpResponse<IInventory>;
export type EntityArrayResponseType = HttpResponse<IInventory[]>;

@Injectable({ providedIn: 'root' })
export class InventoryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/inventories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(inventory: IInventory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventory);
    return this.http
      .post<IInventory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inventory: IInventory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventory);
    return this.http
      .put<IInventory>(`${this.resourceUrl}/${getInventoryIdentifier(inventory) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(inventory: IInventory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventory);
    return this.http
      .patch<IInventory>(`${this.resourceUrl}/${getInventoryIdentifier(inventory) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInventory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInventory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addInventoryToCollectionIfMissing(
    inventoryCollection: IInventory[],
    ...inventoriesToCheck: (IInventory | null | undefined)[]
  ): IInventory[] {
    const inventories: IInventory[] = inventoriesToCheck.filter(isPresent);
    if (inventories.length > 0) {
      const inventoryCollectionIdentifiers = inventoryCollection.map(inventoryItem => getInventoryIdentifier(inventoryItem)!);
      const inventoriesToAdd = inventories.filter(inventoryItem => {
        const inventoryIdentifier = getInventoryIdentifier(inventoryItem);
        if (inventoryIdentifier == null || inventoryCollectionIdentifiers.includes(inventoryIdentifier)) {
          return false;
        }
        inventoryCollectionIdentifiers.push(inventoryIdentifier);
        return true;
      });
      return [...inventoriesToAdd, ...inventoryCollection];
    }
    return inventoryCollection;
  }

  protected convertDateFromClient(inventory: IInventory): IInventory {
    return Object.assign({}, inventory, {
      lastModified: inventory.lastModified?.isValid() ? inventory.lastModified.toJSON() : undefined,
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
      res.body.forEach((inventory: IInventory) => {
        inventory.lastModified = inventory.lastModified ? dayjs(inventory.lastModified) : undefined;
      });
    }
    return res;
  }
}
