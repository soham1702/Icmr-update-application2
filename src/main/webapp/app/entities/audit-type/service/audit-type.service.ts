import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAuditType, getAuditTypeIdentifier } from '../audit-type.model';

export type EntityResponseType = HttpResponse<IAuditType>;
export type EntityArrayResponseType = HttpResponse<IAuditType[]>;

@Injectable({ providedIn: 'root' })
export class AuditTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/audit-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(auditType: IAuditType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditType);
    return this.http
      .post<IAuditType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(auditType: IAuditType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditType);
    return this.http
      .put<IAuditType>(`${this.resourceUrl}/${getAuditTypeIdentifier(auditType) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(auditType: IAuditType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditType);
    return this.http
      .patch<IAuditType>(`${this.resourceUrl}/${getAuditTypeIdentifier(auditType) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAuditType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAuditType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAuditTypeToCollectionIfMissing(
    auditTypeCollection: IAuditType[],
    ...auditTypesToCheck: (IAuditType | null | undefined)[]
  ): IAuditType[] {
    const auditTypes: IAuditType[] = auditTypesToCheck.filter(isPresent);
    if (auditTypes.length > 0) {
      const auditTypeCollectionIdentifiers = auditTypeCollection.map(auditTypeItem => getAuditTypeIdentifier(auditTypeItem)!);
      const auditTypesToAdd = auditTypes.filter(auditTypeItem => {
        const auditTypeIdentifier = getAuditTypeIdentifier(auditTypeItem);
        if (auditTypeIdentifier == null || auditTypeCollectionIdentifiers.includes(auditTypeIdentifier)) {
          return false;
        }
        auditTypeCollectionIdentifiers.push(auditTypeIdentifier);
        return true;
      });
      return [...auditTypesToAdd, ...auditTypeCollection];
    }
    return auditTypeCollection;
  }

  protected convertDateFromClient(auditType: IAuditType): IAuditType {
    return Object.assign({}, auditType, {
      lastModified: auditType.lastModified?.isValid() ? auditType.lastModified.toJSON() : undefined,
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
      res.body.forEach((auditType: IAuditType) => {
        auditType.lastModified = auditType.lastModified ? dayjs(auditType.lastModified) : undefined;
      });
    }
    return res;
  }
}
