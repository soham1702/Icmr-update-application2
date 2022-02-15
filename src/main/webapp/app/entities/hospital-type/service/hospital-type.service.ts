import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHospitalType, getHospitalTypeIdentifier } from '../hospital-type.model';

export type EntityResponseType = HttpResponse<IHospitalType>;
export type EntityArrayResponseType = HttpResponse<IHospitalType[]>;

@Injectable({ providedIn: 'root' })
export class HospitalTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/hospital-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(hospitalType: IHospitalType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hospitalType);
    return this.http
      .post<IHospitalType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(hospitalType: IHospitalType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hospitalType);
    return this.http
      .put<IHospitalType>(`${this.resourceUrl}/${getHospitalTypeIdentifier(hospitalType) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(hospitalType: IHospitalType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hospitalType);
    return this.http
      .patch<IHospitalType>(`${this.resourceUrl}/${getHospitalTypeIdentifier(hospitalType) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHospitalType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHospitalType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHospitalTypeToCollectionIfMissing(
    hospitalTypeCollection: IHospitalType[],
    ...hospitalTypesToCheck: (IHospitalType | null | undefined)[]
  ): IHospitalType[] {
    const hospitalTypes: IHospitalType[] = hospitalTypesToCheck.filter(isPresent);
    if (hospitalTypes.length > 0) {
      const hospitalTypeCollectionIdentifiers = hospitalTypeCollection.map(
        hospitalTypeItem => getHospitalTypeIdentifier(hospitalTypeItem)!
      );
      const hospitalTypesToAdd = hospitalTypes.filter(hospitalTypeItem => {
        const hospitalTypeIdentifier = getHospitalTypeIdentifier(hospitalTypeItem);
        if (hospitalTypeIdentifier == null || hospitalTypeCollectionIdentifiers.includes(hospitalTypeIdentifier)) {
          return false;
        }
        hospitalTypeCollectionIdentifiers.push(hospitalTypeIdentifier);
        return true;
      });
      return [...hospitalTypesToAdd, ...hospitalTypeCollection];
    }
    return hospitalTypeCollection;
  }

  protected convertDateFromClient(hospitalType: IHospitalType): IHospitalType {
    return Object.assign({}, hospitalType, {
      lastModified: hospitalType.lastModified?.isValid() ? hospitalType.lastModified.toJSON() : undefined,
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
      res.body.forEach((hospitalType: IHospitalType) => {
        hospitalType.lastModified = hospitalType.lastModified ? dayjs(hospitalType.lastModified) : undefined;
      });
    }
    return res;
  }
}
