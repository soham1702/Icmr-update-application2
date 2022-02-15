import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDistrict, getDistrictIdentifier } from '../district.model';

export type EntityResponseType = HttpResponse<IDistrict>;
export type EntityArrayResponseType = HttpResponse<IDistrict[]>;

@Injectable({ providedIn: 'root' })
export class DistrictService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/districts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(district: IDistrict): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(district);
    return this.http
      .post<IDistrict>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(district: IDistrict): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(district);
    return this.http
      .put<IDistrict>(`${this.resourceUrl}/${getDistrictIdentifier(district) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(district: IDistrict): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(district);
    return this.http
      .patch<IDistrict>(`${this.resourceUrl}/${getDistrictIdentifier(district) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDistrict>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDistrict[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDistrictToCollectionIfMissing(districtCollection: IDistrict[], ...districtsToCheck: (IDistrict | null | undefined)[]): IDistrict[] {
    const districts: IDistrict[] = districtsToCheck.filter(isPresent);
    if (districts.length > 0) {
      const districtCollectionIdentifiers = districtCollection.map(districtItem => getDistrictIdentifier(districtItem)!);
      const districtsToAdd = districts.filter(districtItem => {
        const districtIdentifier = getDistrictIdentifier(districtItem);
        if (districtIdentifier == null || districtCollectionIdentifiers.includes(districtIdentifier)) {
          return false;
        }
        districtCollectionIdentifiers.push(districtIdentifier);
        return true;
      });
      return [...districtsToAdd, ...districtCollection];
    }
    return districtCollection;
  }

  protected convertDateFromClient(district: IDistrict): IDistrict {
    return Object.assign({}, district, {
      lastModified: district.lastModified?.isValid() ? district.lastModified.toJSON() : undefined,
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
      res.body.forEach((district: IDistrict) => {
        district.lastModified = district.lastModified ? dayjs(district.lastModified) : undefined;
      });
    }
    return res;
  }
}
