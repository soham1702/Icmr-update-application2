import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IBedTransactions, BedTransactions } from '../bed-transactions.model';

import { BedTransactionsService } from './bed-transactions.service';

describe('BedTransactions Service', () => {
  let service: BedTransactionsService;
  let httpMock: HttpTestingController;
  let elemDefault: IBedTransactions;
  let expectedResult: IBedTransactions | IBedTransactions[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BedTransactionsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      occupied: 0,
      onCylinder: 0,
      onLMO: 0,
      onConcentrators: 0,
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

    it('should create a BedTransactions', () => {
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

      service.create(new BedTransactions()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BedTransactions', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          occupied: 1,
          onCylinder: 1,
          onLMO: 1,
          onConcentrators: 1,
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

    it('should partial update a BedTransactions', () => {
      const patchObject = Object.assign(
        {
          onLMO: 1,
        },
        new BedTransactions()
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

    it('should return a list of BedTransactions', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          occupied: 1,
          onCylinder: 1,
          onLMO: 1,
          onConcentrators: 1,
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

    it('should delete a BedTransactions', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBedTransactionsToCollectionIfMissing', () => {
      it('should add a BedTransactions to an empty array', () => {
        const bedTransactions: IBedTransactions = { id: 123 };
        expectedResult = service.addBedTransactionsToCollectionIfMissing([], bedTransactions);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bedTransactions);
      });

      it('should not add a BedTransactions to an array that contains it', () => {
        const bedTransactions: IBedTransactions = { id: 123 };
        const bedTransactionsCollection: IBedTransactions[] = [
          {
            ...bedTransactions,
          },
          { id: 456 },
        ];
        expectedResult = service.addBedTransactionsToCollectionIfMissing(bedTransactionsCollection, bedTransactions);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BedTransactions to an array that doesn't contain it", () => {
        const bedTransactions: IBedTransactions = { id: 123 };
        const bedTransactionsCollection: IBedTransactions[] = [{ id: 456 }];
        expectedResult = service.addBedTransactionsToCollectionIfMissing(bedTransactionsCollection, bedTransactions);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bedTransactions);
      });

      it('should add only unique BedTransactions to an array', () => {
        const bedTransactionsArray: IBedTransactions[] = [{ id: 123 }, { id: 456 }, { id: 12849 }];
        const bedTransactionsCollection: IBedTransactions[] = [{ id: 123 }];
        expectedResult = service.addBedTransactionsToCollectionIfMissing(bedTransactionsCollection, ...bedTransactionsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bedTransactions: IBedTransactions = { id: 123 };
        const bedTransactions2: IBedTransactions = { id: 456 };
        expectedResult = service.addBedTransactionsToCollectionIfMissing([], bedTransactions, bedTransactions2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bedTransactions);
        expect(expectedResult).toContain(bedTransactions2);
      });

      it('should accept null and undefined values', () => {
        const bedTransactions: IBedTransactions = { id: 123 };
        expectedResult = service.addBedTransactionsToCollectionIfMissing([], null, bedTransactions, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bedTransactions);
      });

      it('should return initial array if no BedTransactions is added', () => {
        const bedTransactionsCollection: IBedTransactions[] = [{ id: 123 }];
        expectedResult = service.addBedTransactionsToCollectionIfMissing(bedTransactionsCollection, undefined, null);
        expect(expectedResult).toEqual(bedTransactionsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
