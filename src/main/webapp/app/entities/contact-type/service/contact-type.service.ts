import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContactType, getContactTypeIdentifier } from '../contact-type.model';

export type EntityResponseType = HttpResponse<IContactType>;
export type EntityArrayResponseType = HttpResponse<IContactType[]>;

@Injectable({ providedIn: 'root' })
export class ContactTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contact-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(contactType: IContactType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contactType);
    return this.http
      .post<IContactType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contactType: IContactType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contactType);
    return this.http
      .put<IContactType>(`${this.resourceUrl}/${getContactTypeIdentifier(contactType) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(contactType: IContactType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contactType);
    return this.http
      .patch<IContactType>(`${this.resourceUrl}/${getContactTypeIdentifier(contactType) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContactType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContactType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addContactTypeToCollectionIfMissing(
    contactTypeCollection: IContactType[],
    ...contactTypesToCheck: (IContactType | null | undefined)[]
  ): IContactType[] {
    const contactTypes: IContactType[] = contactTypesToCheck.filter(isPresent);
    if (contactTypes.length > 0) {
      const contactTypeCollectionIdentifiers = contactTypeCollection.map(contactTypeItem => getContactTypeIdentifier(contactTypeItem)!);
      const contactTypesToAdd = contactTypes.filter(contactTypeItem => {
        const contactTypeIdentifier = getContactTypeIdentifier(contactTypeItem);
        if (contactTypeIdentifier == null || contactTypeCollectionIdentifiers.includes(contactTypeIdentifier)) {
          return false;
        }
        contactTypeCollectionIdentifiers.push(contactTypeIdentifier);
        return true;
      });
      return [...contactTypesToAdd, ...contactTypeCollection];
    }
    return contactTypeCollection;
  }

  protected convertDateFromClient(contactType: IContactType): IContactType {
    return Object.assign({}, contactType, {
      lastModified: contactType.lastModified?.isValid() ? contactType.lastModified.toJSON() : undefined,
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
      res.body.forEach((contactType: IContactType) => {
        contactType.lastModified = contactType.lastModified ? dayjs(contactType.lastModified) : undefined;
      });
    }
    return res;
  }
}
