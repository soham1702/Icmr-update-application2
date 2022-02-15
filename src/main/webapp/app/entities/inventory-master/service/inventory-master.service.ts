import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInventoryMaster, getInventoryMasterIdentifier } from '../inventory-master.model';

export type EntityResponseType = HttpResponse<IInventoryMaster>;
export type EntityArrayResponseType = HttpResponse<IInventoryMaster[]>;

@Injectable({ providedIn: 'root' })
export class InventoryMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/inventory-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(inventoryMaster: IInventoryMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryMaster);
    return this.http
      .post<IInventoryMaster>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inventoryMaster: IInventoryMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryMaster);
    return this.http
      .put<IInventoryMaster>(`${this.resourceUrl}/${getInventoryMasterIdentifier(inventoryMaster) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(inventoryMaster: IInventoryMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryMaster);
    return this.http
      .patch<IInventoryMaster>(`${this.resourceUrl}/${getInventoryMasterIdentifier(inventoryMaster) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInventoryMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInventoryMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addInventoryMasterToCollectionIfMissing(
    inventoryMasterCollection: IInventoryMaster[],
    ...inventoryMastersToCheck: (IInventoryMaster | null | undefined)[]
  ): IInventoryMaster[] {
    const inventoryMasters: IInventoryMaster[] = inventoryMastersToCheck.filter(isPresent);
    if (inventoryMasters.length > 0) {
      const inventoryMasterCollectionIdentifiers = inventoryMasterCollection.map(
        inventoryMasterItem => getInventoryMasterIdentifier(inventoryMasterItem)!
      );
      const inventoryMastersToAdd = inventoryMasters.filter(inventoryMasterItem => {
        const inventoryMasterIdentifier = getInventoryMasterIdentifier(inventoryMasterItem);
        if (inventoryMasterIdentifier == null || inventoryMasterCollectionIdentifiers.includes(inventoryMasterIdentifier)) {
          return false;
        }
        inventoryMasterCollectionIdentifiers.push(inventoryMasterIdentifier);
        return true;
      });
      return [...inventoryMastersToAdd, ...inventoryMasterCollection];
    }
    return inventoryMasterCollection;
  }

  protected convertDateFromClient(inventoryMaster: IInventoryMaster): IInventoryMaster {
    return Object.assign({}, inventoryMaster, {
      lastModified: inventoryMaster.lastModified?.isValid() ? inventoryMaster.lastModified.toJSON() : undefined,
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
      res.body.forEach((inventoryMaster: IInventoryMaster) => {
        inventoryMaster.lastModified = inventoryMaster.lastModified ? dayjs(inventoryMaster.lastModified) : undefined;
      });
    }
    return res;
  }
}
