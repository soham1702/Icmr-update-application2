import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IHospitalType, HospitalType } from '../hospital-type.model';

import { HospitalTypeService } from './hospital-type.service';

describe('HospitalType Service', () => {
  let service: HospitalTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: IHospitalType;
  let expectedResult: IHospitalType | IHospitalType[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(HospitalTypeService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      desciption: 'AAAAAAA',
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

    it('should create a HospitalType', () => {
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

      service.create(new HospitalType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a HospitalType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          desciption: 'BBBBBB',
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

    it('should partial update a HospitalType', () => {
      const patchObject = Object.assign(
        {
          desciption: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        new HospitalType()
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

    it('should return a list of HospitalType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          desciption: 'BBBBBB',
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

    it('should delete a HospitalType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addHospitalTypeToCollectionIfMissing', () => {
      it('should add a HospitalType to an empty array', () => {
        const hospitalType: IHospitalType = { id: 123 };
        expectedResult = service.addHospitalTypeToCollectionIfMissing([], hospitalType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hospitalType);
      });

      it('should not add a HospitalType to an array that contains it', () => {
        const hospitalType: IHospitalType = { id: 123 };
        const hospitalTypeCollection: IHospitalType[] = [
          {
            ...hospitalType,
          },
          { id: 456 },
        ];
        expectedResult = service.addHospitalTypeToCollectionIfMissing(hospitalTypeCollection, hospitalType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a HospitalType to an array that doesn't contain it", () => {
        const hospitalType: IHospitalType = { id: 123 };
        const hospitalTypeCollection: IHospitalType[] = [{ id: 456 }];
        expectedResult = service.addHospitalTypeToCollectionIfMissing(hospitalTypeCollection, hospitalType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hospitalType);
      });

      it('should add only unique HospitalType to an array', () => {
        const hospitalTypeArray: IHospitalType[] = [{ id: 123 }, { id: 456 }, { id: 48209 }];
        const hospitalTypeCollection: IHospitalType[] = [{ id: 123 }];
        expectedResult = service.addHospitalTypeToCollectionIfMissing(hospitalTypeCollection, ...hospitalTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const hospitalType: IHospitalType = { id: 123 };
        const hospitalType2: IHospitalType = { id: 456 };
        expectedResult = service.addHospitalTypeToCollectionIfMissing([], hospitalType, hospitalType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hospitalType);
        expect(expectedResult).toContain(hospitalType2);
      });

      it('should accept null and undefined values', () => {
        const hospitalType: IHospitalType = { id: 123 };
        expectedResult = service.addHospitalTypeToCollectionIfMissing([], null, hospitalType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hospitalType);
      });

      it('should return initial array if no HospitalType is added', () => {
        const hospitalTypeCollection: IHospitalType[] = [{ id: 123 }];
        expectedResult = service.addHospitalTypeToCollectionIfMissing(hospitalTypeCollection, undefined, null);
        expect(expectedResult).toEqual(hospitalTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
