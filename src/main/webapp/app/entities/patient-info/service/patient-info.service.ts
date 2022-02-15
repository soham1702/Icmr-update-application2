import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPatientInfo, getPatientInfoIdentifier } from '../patient-info.model';

export type EntityResponseType = HttpResponse<IPatientInfo>;
export type EntityArrayResponseType = HttpResponse<IPatientInfo[]>;

@Injectable({ providedIn: 'root' })
export class PatientInfoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/patient-infos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(patientInfo: IPatientInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patientInfo);
    return this.http
      .post<IPatientInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(patientInfo: IPatientInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patientInfo);
    return this.http
      .put<IPatientInfo>(`${this.resourceUrl}/${getPatientInfoIdentifier(patientInfo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(patientInfo: IPatientInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patientInfo);
    return this.http
      .patch<IPatientInfo>(`${this.resourceUrl}/${getPatientInfoIdentifier(patientInfo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPatientInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPatientInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPatientInfoToCollectionIfMissing(
    patientInfoCollection: IPatientInfo[],
    ...patientInfosToCheck: (IPatientInfo | null | undefined)[]
  ): IPatientInfo[] {
    const patientInfos: IPatientInfo[] = patientInfosToCheck.filter(isPresent);
    if (patientInfos.length > 0) {
      const patientInfoCollectionIdentifiers = patientInfoCollection.map(patientInfoItem => getPatientInfoIdentifier(patientInfoItem)!);
      const patientInfosToAdd = patientInfos.filter(patientInfoItem => {
        const patientInfoIdentifier = getPatientInfoIdentifier(patientInfoItem);
        if (patientInfoIdentifier == null || patientInfoCollectionIdentifiers.includes(patientInfoIdentifier)) {
          return false;
        }
        patientInfoCollectionIdentifiers.push(patientInfoIdentifier);
        return true;
      });
      return [...patientInfosToAdd, ...patientInfoCollection];
    }
    return patientInfoCollection;
  }

  protected convertDateFromClient(patientInfo: IPatientInfo): IPatientInfo {
    return Object.assign({}, patientInfo, {
      dateOfSampleCollection: patientInfo.dateOfSampleCollection?.isValid() ? patientInfo.dateOfSampleCollection.toJSON() : undefined,
      dateOfSampleReceived: patientInfo.dateOfSampleReceived?.isValid() ? patientInfo.dateOfSampleReceived.toJSON() : undefined,
      hospitalizationDate: patientInfo.hospitalizationDate?.isValid() ? patientInfo.hospitalizationDate.toJSON() : undefined,
      dateOfSampleTested: patientInfo.dateOfSampleTested?.isValid() ? patientInfo.dateOfSampleTested.toJSON() : undefined,
      entryDate: patientInfo.entryDate?.isValid() ? patientInfo.entryDate.toJSON() : undefined,
      confirmationDate: patientInfo.confirmationDate?.isValid() ? patientInfo.confirmationDate.toJSON() : undefined,
      editedOn: patientInfo.editedOn?.isValid() ? patientInfo.editedOn.toJSON() : undefined,
      ccmsPullDate: patientInfo.ccmsPullDate?.isValid() ? patientInfo.ccmsPullDate.toJSON() : undefined,
      lastModified: patientInfo.lastModified?.isValid() ? patientInfo.lastModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfSampleCollection = res.body.dateOfSampleCollection ? dayjs(res.body.dateOfSampleCollection) : undefined;
      res.body.dateOfSampleReceived = res.body.dateOfSampleReceived ? dayjs(res.body.dateOfSampleReceived) : undefined;
      res.body.hospitalizationDate = res.body.hospitalizationDate ? dayjs(res.body.hospitalizationDate) : undefined;
      res.body.dateOfSampleTested = res.body.dateOfSampleTested ? dayjs(res.body.dateOfSampleTested) : undefined;
      res.body.entryDate = res.body.entryDate ? dayjs(res.body.entryDate) : undefined;
      res.body.confirmationDate = res.body.confirmationDate ? dayjs(res.body.confirmationDate) : undefined;
      res.body.editedOn = res.body.editedOn ? dayjs(res.body.editedOn) : undefined;
      res.body.ccmsPullDate = res.body.ccmsPullDate ? dayjs(res.body.ccmsPullDate) : undefined;
      res.body.lastModified = res.body.lastModified ? dayjs(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((patientInfo: IPatientInfo) => {
        patientInfo.dateOfSampleCollection = patientInfo.dateOfSampleCollection ? dayjs(patientInfo.dateOfSampleCollection) : undefined;
        patientInfo.dateOfSampleReceived = patientInfo.dateOfSampleReceived ? dayjs(patientInfo.dateOfSampleReceived) : undefined;
        patientInfo.hospitalizationDate = patientInfo.hospitalizationDate ? dayjs(patientInfo.hospitalizationDate) : undefined;
        patientInfo.dateOfSampleTested = patientInfo.dateOfSampleTested ? dayjs(patientInfo.dateOfSampleTested) : undefined;
        patientInfo.entryDate = patientInfo.entryDate ? dayjs(patientInfo.entryDate) : undefined;
        patientInfo.confirmationDate = patientInfo.confirmationDate ? dayjs(patientInfo.confirmationDate) : undefined;
        patientInfo.editedOn = patientInfo.editedOn ? dayjs(patientInfo.editedOn) : undefined;
        patientInfo.ccmsPullDate = patientInfo.ccmsPullDate ? dayjs(patientInfo.ccmsPullDate) : undefined;
        patientInfo.lastModified = patientInfo.lastModified ? dayjs(patientInfo.lastModified) : undefined;
      });
    }
    return res;
  }
}
