import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBedInventory, getBedInventoryIdentifier } from '../bed-inventory.model';

export type EntityResponseType = HttpResponse<IBedInventory>;
export type EntityArrayResponseType = HttpResponse<IBedInventory[]>;

@Injectable({ providedIn: 'root' })
export class BedInventoryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bed-inventories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bedInventory: IBedInventory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bedInventory);
    return this.http
      .post<IBedInventory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bedInventory: IBedInventory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bedInventory);
    return this.http
      .put<IBedInventory>(`${this.resourceUrl}/${getBedInventoryIdentifier(bedInventory) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(bedInventory: IBedInventory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bedInventory);
    return this.http
      .patch<IBedInventory>(`${this.resourceUrl}/${getBedInventoryIdentifier(bedInventory) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBedInventory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBedInventory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBedInventoryToCollectionIfMissing(
    bedInventoryCollection: IBedInventory[],
    ...bedInventoriesToCheck: (IBedInventory | null | undefined)[]
  ): IBedInventory[] {
    const bedInventories: IBedInventory[] = bedInventoriesToCheck.filter(isPresent);
    if (bedInventories.length > 0) {
      const bedInventoryCollectionIdentifiers = bedInventoryCollection.map(
        bedInventoryItem => getBedInventoryIdentifier(bedInventoryItem)!
      );
      const bedInventoriesToAdd = bedInventories.filter(bedInventoryItem => {
        const bedInventoryIdentifier = getBedInventoryIdentifier(bedInventoryItem);
        if (bedInventoryIdentifier == null || bedInventoryCollectionIdentifiers.includes(bedInventoryIdentifier)) {
          return false;
        }
        bedInventoryCollectionIdentifiers.push(bedInventoryIdentifier);
        return true;
      });
      return [...bedInventoriesToAdd, ...bedInventoryCollection];
    }
    return bedInventoryCollection;
  }

  protected convertDateFromClient(bedInventory: IBedInventory): IBedInventory {
    return Object.assign({}, bedInventory, {
      lastModified: bedInventory.lastModified?.isValid() ? bedInventory.lastModified.toJSON() : undefined,
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
      res.body.forEach((bedInventory: IBedInventory) => {
        bedInventory.lastModified = bedInventory.lastModified ? dayjs(bedInventory.lastModified) : undefined;
      });
    }
    return res;
  }
}
