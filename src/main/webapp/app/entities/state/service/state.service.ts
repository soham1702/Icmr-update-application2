import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IState, getStateIdentifier } from '../state.model';

export type EntityResponseType = HttpResponse<IState>;
export type EntityArrayResponseType = HttpResponse<IState[]>;

@Injectable({ providedIn: 'root' })
export class StateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/states');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(state: IState): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(state);
    return this.http
      .post<IState>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(state: IState): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(state);
    return this.http
      .put<IState>(`${this.resourceUrl}/${getStateIdentifier(state) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(state: IState): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(state);
    return this.http
      .patch<IState>(`${this.resourceUrl}/${getStateIdentifier(state) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IState>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IState[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addStateToCollectionIfMissing(stateCollection: IState[], ...statesToCheck: (IState | null | undefined)[]): IState[] {
    const states: IState[] = statesToCheck.filter(isPresent);
    if (states.length > 0) {
      const stateCollectionIdentifiers = stateCollection.map(stateItem => getStateIdentifier(stateItem)!);
      const statesToAdd = states.filter(stateItem => {
        const stateIdentifier = getStateIdentifier(stateItem);
        if (stateIdentifier == null || stateCollectionIdentifiers.includes(stateIdentifier)) {
          return false;
        }
        stateCollectionIdentifiers.push(stateIdentifier);
        return true;
      });
      return [...statesToAdd, ...stateCollection];
    }
    return stateCollection;
  }

  protected convertDateFromClient(state: IState): IState {
    return Object.assign({}, state, {
      lastModified: state.lastModified?.isValid() ? state.lastModified.toJSON() : undefined,
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
      res.body.forEach((state: IState) => {
        state.lastModified = state.lastModified ? dayjs(state.lastModified) : undefined;
      });
    }
    return res;
  }
}
