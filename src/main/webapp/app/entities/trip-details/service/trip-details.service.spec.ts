import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { TransactionStatus } from 'app/entities/enumerations/transaction-status.model';
import { ITripDetails, TripDetails } from '../trip-details.model';

import { TripDetailsService } from './trip-details.service';

describe('TripDetails Service', () => {
  let service: TripDetailsService;
  let httpMock: HttpTestingController;
  let elemDefault: ITripDetails;
  let expectedResult: ITripDetails | ITripDetails[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TripDetailsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      stockSent: 0,
      stockRec: 0,
      comment: 'AAAAAAA',
      receiverComment: 'AAAAAAA',
      status: TransactionStatus.OPEN,
      createdDate: currentDate,
      createdBy: 'AAAAAAA',
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TripDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new TripDetails()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TripDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          stockSent: 1,
          stockRec: 1,
          comment: 'BBBBBB',
          receiverComment: 'BBBBBB',
          status: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TripDetails', () => {
      const patchObject = Object.assign(
        {
          status: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        new TripDetails()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TripDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          stockSent: 1,
          stockRec: 1,
          comment: 'BBBBBB',
          receiverComment: 'BBBBBB',
          status: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDate: currentDate,
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

    it('should delete a TripDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTripDetailsToCollectionIfMissing', () => {
      it('should add a TripDetails to an empty array', () => {
        const tripDetails: ITripDetails = { id: 123 };
        expectedResult = service.addTripDetailsToCollectionIfMissing([], tripDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tripDetails);
      });

      it('should not add a TripDetails to an array that contains it', () => {
        const tripDetails: ITripDetails = { id: 123 };
        const tripDetailsCollection: ITripDetails[] = [
          {
            ...tripDetails,
          },
          { id: 456 },
        ];
        expectedResult = service.addTripDetailsToCollectionIfMissing(tripDetailsCollection, tripDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TripDetails to an array that doesn't contain it", () => {
        const tripDetails: ITripDetails = { id: 123 };
        const tripDetailsCollection: ITripDetails[] = [{ id: 456 }];
        expectedResult = service.addTripDetailsToCollectionIfMissing(tripDetailsCollection, tripDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tripDetails);
      });

      it('should add only unique TripDetails to an array', () => {
        const tripDetailsArray: ITripDetails[] = [{ id: 123 }, { id: 456 }, { id: 23704 }];
        const tripDetailsCollection: ITripDetails[] = [{ id: 123 }];
        expectedResult = service.addTripDetailsToCollectionIfMissing(tripDetailsCollection, ...tripDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tripDetails: ITripDetails = { id: 123 };
        const tripDetails2: ITripDetails = { id: 456 };
        expectedResult = service.addTripDetailsToCollectionIfMissing([], tripDetails, tripDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tripDetails);
        expect(expectedResult).toContain(tripDetails2);
      });

      it('should accept null and undefined values', () => {
        const tripDetails: ITripDetails = { id: 123 };
        expectedResult = service.addTripDetailsToCollectionIfMissing([], null, tripDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tripDetails);
      });

      it('should return initial array if no TripDetails is added', () => {
        const tripDetailsCollection: ITripDetails[] = [{ id: 123 }];
        expectedResult = service.addTripDetailsToCollectionIfMissing(tripDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(tripDetailsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
