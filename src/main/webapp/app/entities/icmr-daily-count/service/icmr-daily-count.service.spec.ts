import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IICMRDailyCount, ICMRDailyCount } from '../icmr-daily-count.model';

import { ICMRDailyCountService } from './icmr-daily-count.service';

describe('ICMRDailyCount Service', () => {
  let service: ICMRDailyCountService;
  let httpMock: HttpTestingController;
  let elemDefault: IICMRDailyCount;
  let expectedResult: IICMRDailyCount | IICMRDailyCount[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ICMRDailyCountService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      totalSamplesTested: 0,
      totalNegative: 0,
      totalPositive: 0,
      currentPreviousMonthTest: 0,
      districtId: 0,
      remarks: 'AAAAAAA',
      editedOn: currentDate,
      updatedDate: currentDate,
      freeField1: 'AAAAAAA',
      freeField2: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          editedOn: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ICMRDailyCount', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          editedOn: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          editedOn: currentDate,
          updatedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new ICMRDailyCount()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ICMRDailyCount', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          totalSamplesTested: 1,
          totalNegative: 1,
          totalPositive: 1,
          currentPreviousMonthTest: 1,
          districtId: 1,
          remarks: 'BBBBBB',
          editedOn: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          editedOn: currentDate,
          updatedDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ICMRDailyCount', () => {
      const patchObject = Object.assign(
        {
          totalPositive: 1,
          currentPreviousMonthTest: 1,
          editedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new ICMRDailyCount()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          editedOn: currentDate,
          updatedDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ICMRDailyCount', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          totalSamplesTested: 1,
          totalNegative: 1,
          totalPositive: 1,
          currentPreviousMonthTest: 1,
          districtId: 1,
          remarks: 'BBBBBB',
          editedOn: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          editedOn: currentDate,
          updatedDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ICMRDailyCount', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addICMRDailyCountToCollectionIfMissing', () => {
      it('should add a ICMRDailyCount to an empty array', () => {
        const iCMRDailyCount: IICMRDailyCount = { id: 123 };
        expectedResult = service.addICMRDailyCountToCollectionIfMissing([], iCMRDailyCount);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(iCMRDailyCount);
      });

      it('should not add a ICMRDailyCount to an array that contains it', () => {
        const iCMRDailyCount: IICMRDailyCount = { id: 123 };
        const iCMRDailyCountCollection: IICMRDailyCount[] = [
          {
            ...iCMRDailyCount,
          },
          { id: 456 },
        ];
        expectedResult = service.addICMRDailyCountToCollectionIfMissing(iCMRDailyCountCollection, iCMRDailyCount);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ICMRDailyCount to an array that doesn't contain it", () => {
        const iCMRDailyCount: IICMRDailyCount = { id: 123 };
        const iCMRDailyCountCollection: IICMRDailyCount[] = [{ id: 456 }];
        expectedResult = service.addICMRDailyCountToCollectionIfMissing(iCMRDailyCountCollection, iCMRDailyCount);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(iCMRDailyCount);
      });

      it('should add only unique ICMRDailyCount to an array', () => {
        const iCMRDailyCountArray: IICMRDailyCount[] = [{ id: 123 }, { id: 456 }, { id: 9172 }];
        const iCMRDailyCountCollection: IICMRDailyCount[] = [{ id: 123 }];
        expectedResult = service.addICMRDailyCountToCollectionIfMissing(iCMRDailyCountCollection, ...iCMRDailyCountArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const iCMRDailyCount: IICMRDailyCount = { id: 123 };
        const iCMRDailyCount2: IICMRDailyCount = { id: 456 };
        expectedResult = service.addICMRDailyCountToCollectionIfMissing([], iCMRDailyCount, iCMRDailyCount2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(iCMRDailyCount);
        expect(expectedResult).toContain(iCMRDailyCount2);
      });

      it('should accept null and undefined values', () => {
        const iCMRDailyCount: IICMRDailyCount = { id: 123 };
        expectedResult = service.addICMRDailyCountToCollectionIfMissing([], null, iCMRDailyCount, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(iCMRDailyCount);
      });

      it('should return initial array if no ICMRDailyCount is added', () => {
        const iCMRDailyCountCollection: IICMRDailyCount[] = [{ id: 123 }];
        expectedResult = service.addICMRDailyCountToCollectionIfMissing(iCMRDailyCountCollection, undefined, null);
        expect(expectedResult).toEqual(iCMRDailyCountCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
