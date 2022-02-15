import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IBedType, BedType } from '../bed-type.model';

import { BedTypeService } from './bed-type.service';

describe('BedType Service', () => {
  let service: BedTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: IBedType;
  let expectedResult: IBedType | IBedType[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BedTypeService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      perDayOX: 0,
      deleted: false,
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

    it('should create a BedType', () => {
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

      service.create(new BedType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BedType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          perDayOX: 1,
          deleted: true,
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

    it('should partial update a BedType', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          perDayOX: 1,
          deleted: true,
        },
        new BedType()
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

    it('should return a list of BedType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          perDayOX: 1,
          deleted: true,
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

    it('should delete a BedType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBedTypeToCollectionIfMissing', () => {
      it('should add a BedType to an empty array', () => {
        const bedType: IBedType = { id: 123 };
        expectedResult = service.addBedTypeToCollectionIfMissing([], bedType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bedType);
      });

      it('should not add a BedType to an array that contains it', () => {
        const bedType: IBedType = { id: 123 };
        const bedTypeCollection: IBedType[] = [
          {
            ...bedType,
          },
          { id: 456 },
        ];
        expectedResult = service.addBedTypeToCollectionIfMissing(bedTypeCollection, bedType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BedType to an array that doesn't contain it", () => {
        const bedType: IBedType = { id: 123 };
        const bedTypeCollection: IBedType[] = [{ id: 456 }];
        expectedResult = service.addBedTypeToCollectionIfMissing(bedTypeCollection, bedType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bedType);
      });

      it('should add only unique BedType to an array', () => {
        const bedTypeArray: IBedType[] = [{ id: 123 }, { id: 456 }, { id: 96391 }];
        const bedTypeCollection: IBedType[] = [{ id: 123 }];
        expectedResult = service.addBedTypeToCollectionIfMissing(bedTypeCollection, ...bedTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bedType: IBedType = { id: 123 };
        const bedType2: IBedType = { id: 456 };
        expectedResult = service.addBedTypeToCollectionIfMissing([], bedType, bedType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bedType);
        expect(expectedResult).toContain(bedType2);
      });

      it('should accept null and undefined values', () => {
        const bedType: IBedType = { id: 123 };
        expectedResult = service.addBedTypeToCollectionIfMissing([], null, bedType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bedType);
      });

      it('should return initial array if no BedType is added', () => {
        const bedTypeCollection: IBedType[] = [{ id: 123 }];
        expectedResult = service.addBedTypeToCollectionIfMissing(bedTypeCollection, undefined, null);
        expect(expectedResult).toEqual(bedTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
