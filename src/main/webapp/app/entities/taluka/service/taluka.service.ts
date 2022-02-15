import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaluka, getTalukaIdentifier } from '../taluka.model';

export type EntityResponseType = HttpResponse<ITaluka>;
export type EntityArrayResponseType = HttpResponse<ITaluka[]>;

@Injectable({ providedIn: 'root' })
export class TalukaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/talukas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(taluka: ITaluka): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taluka);
    return this.http
      .post<ITaluka>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taluka: ITaluka): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taluka);
    return this.http
      .put<ITaluka>(`${this.resourceUrl}/${getTalukaIdentifier(taluka) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(taluka: ITaluka): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taluka);
    return this.http
      .patch<ITaluka>(`${this.resourceUrl}/${getTalukaIdentifier(taluka) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaluka>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaluka[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTalukaToCollectionIfMissing(talukaCollection: ITaluka[], ...talukasToCheck: (ITaluka | null | undefined)[]): ITaluka[] {
    const talukas: ITaluka[] = talukasToCheck.filter(isPresent);
    if (talukas.length > 0) {
      const talukaCollectionIdentifiers = talukaCollection.map(talukaItem => getTalukaIdentifier(talukaItem)!);
      const talukasToAdd = talukas.filter(talukaItem => {
        const talukaIdentifier = getTalukaIdentifier(talukaItem);
        if (talukaIdentifier == null || talukaCollectionIdentifiers.includes(talukaIdentifier)) {
          return false;
        }
        talukaCollectionIdentifiers.push(talukaIdentifier);
        return true;
      });
      return [...talukasToAdd, ...talukaCollection];
    }
    return talukaCollection;
  }

  protected convertDateFromClient(taluka: ITaluka): ITaluka {
    return Object.assign({}, taluka, {
      lastModified: taluka.lastModified?.isValid() ? taluka.lastModified.toJSON() : undefined,
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
      res.body.forEach((taluka: ITaluka) => {
        taluka.lastModified = taluka.lastModified ? dayjs(taluka.lastModified) : undefined;
      });
    }
    return res;
  }
}
