import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IState, State } from '../state.model';

import { StateService } from './state.service';

describe('State Service', () => {
  let service: StateService;
  let httpMock: HttpTestingController;
  let elemDefault: IState;
  let expectedResult: IState | IState[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StateService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      deleted: false,
      lgdCode: 0,
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a State', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new State()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a State', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          deleted: true,
          lgdCode: 1,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a State', () => {
      const patchObject = Object.assign(
        {
          lgdCode: 1,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        new State()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of State', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          deleted: true,
          lgdCode: 1,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a State', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addStateToCollectionIfMissing', () => {
      it('should add a State to an empty array', () => {
        const state: IState = { id: 123 };
        expectedResult = service.addStateToCollectionIfMissing([], state);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(state);
      });

      it('should not add a State to an array that contains it', () => {
        const state: IState = { id: 123 };
        const stateCollection: IState[] = [
          {
            ...state,
          },
          { id: 456 },
        ];
        expectedResult = service.addStateToCollectionIfMissing(stateCollection, state);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a State to an array that doesn't contain it", () => {
        const state: IState = { id: 123 };
        const stateCollection: IState[] = [{ id: 456 }];
        expectedResult = service.addStateToCollectionIfMissing(stateCollection, state);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(state);
      });

      it('should add only unique State to an array', () => {
        const stateArray: IState[] = [{ id: 123 }, { id: 456 }, { id: 4519 }];
        const stateCollection: IState[] = [{ id: 123 }];
        expectedResult = service.addStateToCollectionIfMissing(stateCollection, ...stateArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const state: IState = { id: 123 };
        const state2: IState = { id: 456 };
        expectedResult = service.addStateToCollectionIfMissing([], state, state2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(state);
        expect(expectedResult).toContain(state2);
      });

      it('should accept null and undefined values', () => {
        const state: IState = { id: 123 };
        expectedResult = service.addStateToCollectionIfMissing([], null, state, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(state);
      });

      it('should return initial array if no State is added', () => {
        const stateCollection: IState[] = [{ id: 123 }];
        expectedResult = service.addStateToCollectionIfMissing(stateCollection, undefined, null);
        expect(expectedResult).toEqual(stateCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
