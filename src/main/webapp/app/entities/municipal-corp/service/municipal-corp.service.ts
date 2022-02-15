import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMunicipalCorp, getMunicipalCorpIdentifier } from '../municipal-corp.model';

export type EntityResponseType = HttpResponse<IMunicipalCorp>;
export type EntityArrayResponseType = HttpResponse<IMunicipalCorp[]>;

@Injectable({ providedIn: 'root' })
export class MunicipalCorpService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/municipal-corps');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(municipalCorp: IMunicipalCorp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(municipalCorp);
    return this.http
      .post<IMunicipalCorp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(municipalCorp: IMunicipalCorp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(municipalCorp);
    return this.http
      .put<IMunicipalCorp>(`${this.resourceUrl}/${getMunicipalCorpIdentifier(municipalCorp) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(municipalCorp: IMunicipalCorp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(municipalCorp);
    return this.http
      .patch<IMunicipalCorp>(`${this.resourceUrl}/${getMunicipalCorpIdentifier(municipalCorp) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMunicipalCorp>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMunicipalCorp[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMunicipalCorpToCollectionIfMissing(
    municipalCorpCollection: IMunicipalCorp[],
    ...municipalCorpsToCheck: (IMunicipalCorp | null | undefined)[]
  ): IMunicipalCorp[] {
    const municipalCorps: IMunicipalCorp[] = municipalCorpsToCheck.filter(isPresent);
    if (municipalCorps.length > 0) {
      const municipalCorpCollectionIdentifiers = municipalCorpCollection.map(
        municipalCorpItem => getMunicipalCorpIdentifier(municipalCorpItem)!
      );
      const municipalCorpsToAdd = municipalCorps.filter(municipalCorpItem => {
        const municipalCorpIdentifier = getMunicipalCorpIdentifier(municipalCorpItem);
        if (municipalCorpIdentifier == null || municipalCorpCollectionIdentifiers.includes(municipalCorpIdentifier)) {
          return false;
        }
        municipalCorpCollectionIdentifiers.push(municipalCorpIdentifier);
        return true;
      });
      return [...municipalCorpsToAdd, ...municipalCorpCollection];
    }
    return municipalCorpCollection;
  }

  protected convertDateFromClient(municipalCorp: IMunicipalCorp): IMunicipalCorp {
    return Object.assign({}, municipalCorp, {
      lastModified: municipalCorp.lastModified?.isValid() ? municipalCorp.lastModified.toJSON() : undefined,
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
      res.body.forEach((municipalCorp: IMunicipalCorp) => {
        municipalCorp.lastModified = municipalCorp.lastModified ? dayjs(municipalCorp.lastModified) : undefined;
      });
    }
    return res;
  }
}
