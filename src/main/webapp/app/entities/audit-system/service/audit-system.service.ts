import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAuditSystem, getAuditSystemIdentifier } from '../audit-system.model';

export type EntityResponseType = HttpResponse<IAuditSystem>;
export type EntityArrayResponseType = HttpResponse<IAuditSystem[]>;

@Injectable({ providedIn: 'root' })
export class AuditSystemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/audit-systems');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(auditSystem: IAuditSystem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditSystem);
    return this.http
      .post<IAuditSystem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(auditSystem: IAuditSystem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditSystem);
    return this.http
      .put<IAuditSystem>(`${this.resourceUrl}/${getAuditSystemIdentifier(auditSystem) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(auditSystem: IAuditSystem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditSystem);
    return this.http
      .patch<IAuditSystem>(`${this.resourceUrl}/${getAuditSystemIdentifier(auditSystem) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAuditSystem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAuditSystem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAuditSystemToCollectionIfMissing(
    auditSystemCollection: IAuditSystem[],
    ...auditSystemsToCheck: (IAuditSystem | null | undefined)[]
  ): IAuditSystem[] {
    const auditSystems: IAuditSystem[] = auditSystemsToCheck.filter(isPresent);
    if (auditSystems.length > 0) {
      const auditSystemCollectionIdentifiers = auditSystemCollection.map(auditSystemItem => getAuditSystemIdentifier(auditSystemItem)!);
      const auditSystemsToAdd = auditSystems.filter(auditSystemItem => {
        const auditSystemIdentifier = getAuditSystemIdentifier(auditSystemItem);
        if (auditSystemIdentifier == null || auditSystemCollectionIdentifiers.includes(auditSystemIdentifier)) {
          return false;
        }
        auditSystemCollectionIdentifiers.push(auditSystemIdentifier);
        return true;
      });
      return [...auditSystemsToAdd, ...auditSystemCollection];
    }
    return auditSystemCollection;
  }

  protected convertDateFromClient(auditSystem: IAuditSystem): IAuditSystem {
    return Object.assign({}, auditSystem, {
      inspectionDate: auditSystem.inspectionDate?.isValid() ? auditSystem.inspectionDate.toJSON() : undefined,
      lastModified: auditSystem.lastModified?.isValid() ? auditSystem.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.inspectionDate = res.body.inspectionDate ? dayjs(res.body.inspectionDate) : undefined;
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((auditSystem: IAuditSystem) => {
        auditSystem.inspectionDate = auditSystem.inspectionDate ? dayjs(auditSystem.inspectionDate) : undefined;
        auditSystem.lastModified = auditSystem.lastModified ? dayjs(auditSystem.lastModified) : undefined;
      });
    }
    return res;
  }
}
