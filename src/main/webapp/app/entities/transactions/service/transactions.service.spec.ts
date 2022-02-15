import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { TransactionStatus } from 'app/entities/enumerations/transaction-status.model';
import { ITransactions, Transactions } from '../transactions.model';

import { TransactionsService } from './transactions.service';

describe('Transactions Service', () => {
  let service: TransactionsService;
  let httpMock: HttpTestingController;
  let elemDefault: ITransactions;
  let expectedResult: ITransactions | ITransactions[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TransactionsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      stockReq: 0,
      stockProvided: 0,
      status: TransactionStatus.OPEN,
      comment: 'AAAAAAA',
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

    it('should create a Transactions', () => {
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

      service.create(new Transactions()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Transactions', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          stockReq: 1,
          stockProvided: 1,
          status: 'BBBBBB',
          comment: 'BBBBBB',
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

    it('should partial update a Transactions', () => {
      const patchObject = Object.assign(
        {
          stockReq: 1,
          stockProvided: 1,
          status: 'BBBBBB',
          comment: 'BBBBBB',
        },
        new Transactions()
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

    it('should return a list of Transactions', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          stockReq: 1,
          stockProvided: 1,
          status: 'BBBBBB',
          comment: 'BBBBBB',
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

    it('should delete a Transactions', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTransactionsToCollectionIfMissing', () => {
      it('should add a Transactions to an empty array', () => {
        const transactions: ITransactions = { id: 123 };
        expectedResult = service.addTransactionsToCollectionIfMissing([], transactions);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transactions);
      });

      it('should not add a Transactions to an array that contains it', () => {
        const transactions: ITransactions = { id: 123 };
        const transactionsCollection: ITransactions[] = [
          {
            ...transactions,
          },
          { id: 456 },
        ];
        expectedResult = service.addTransactionsToCollectionIfMissing(transactionsCollection, transactions);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Transactions to an array that doesn't contain it", () => {
        const transactions: ITransactions = { id: 123 };
        const transactionsCollection: ITransactions[] = [{ id: 456 }];
        expectedResult = service.addTransactionsToCollectionIfMissing(transactionsCollection, transactions);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transactions);
      });

      it('should add only unique Transactions to an array', () => {
        const transactionsArray: ITransactions[] = [{ id: 123 }, { id: 456 }, { id: 47435 }];
        const transactionsCollection: ITransactions[] = [{ id: 123 }];
        expectedResult = service.addTransactionsToCollectionIfMissing(transactionsCollection, ...transactionsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const transactions: ITransactions = { id: 123 };
        const transactions2: ITransactions = { id: 456 };
        expectedResult = service.addTransactionsToCollectionIfMissing([], transactions, transactions2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transactions);
        expect(expectedResult).toContain(transactions2);
      });

      it('should accept null and undefined values', () => {
        const transactions: ITransactions = { id: 123 };
        expectedResult = service.addTransactionsToCollectionIfMissing([], null, transactions, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transactions);
      });

      it('should return initial array if no Transactions is added', () => {
        const transactionsCollection: ITransactions[] = [{ id: 123 }];
        expectedResult = service.addTransactionsToCollectionIfMissing(transactionsCollection, undefined, null);
        expect(expectedResult).toEqual(transactionsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
