import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHospital, getHospitalIdentifier } from '../hospital.model';

export type EntityResponseType = HttpResponse<IHospital>;
export type EntityArrayResponseType = HttpResponse<IHospital[]>;

@Injectable({ providedIn: 'root' })
export class HospitalService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/hospitals');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(hospital: IHospital): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hospital);
    return this.http
      .post<IHospital>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(hospital: IHospital): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hospital);
    return this.http
      .put<IHospital>(`${this.resourceUrl}/${getHospitalIdentifier(hospital) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(hospital: IHospital): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hospital);
    return this.http
      .patch<IHospital>(`${this.resourceUrl}/${getHospitalIdentifier(hospital) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHospital>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHospital[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHospitalToCollectionIfMissing(hospitalCollection: IHospital[], ...hospitalsToCheck: (IHospital | null | undefined)[]): IHospital[] {
    const hospitals: IHospital[] = hospitalsToCheck.filter(isPresent);
    if (hospitals.length > 0) {
      const hospitalCollectionIdentifiers = hospitalCollection.map(hospitalItem => getHospitalIdentifier(hospitalItem)!);
      const hospitalsToAdd = hospitals.filter(hospitalItem => {
        const hospitalIdentifier = getHospitalIdentifier(hospitalItem);
        if (hospitalIdentifier == null || hospitalCollectionIdentifiers.includes(hospitalIdentifier)) {
          return false;
        }
        hospitalCollectionIdentifiers.push(hospitalIdentifier);
        return true;
      });
      return [...hospitalsToAdd, ...hospitalCollection];
    }
    return hospitalCollection;
  }

  protected convertDateFromClient(hospital: IHospital): IHospital {
    return Object.assign({}, hospital, {
      lastModified: hospital.lastModified?.isValid() ? hospital.lastModified.toJSON() : undefined,
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
      res.body.forEach((hospital: IHospital) => {
        hospital.lastModified = hospital.lastModified ? dayjs(hospital.lastModified) : undefined;
      });
    }
    return res;
  }
}
