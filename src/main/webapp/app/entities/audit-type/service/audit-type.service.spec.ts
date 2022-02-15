import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAuditType, AuditType } from '../audit-type.model';

import { AuditTypeService } from './audit-type.service';

describe('AuditType Service', () => {
  let service: AuditTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: IAuditType;
  let expectedResult: IAuditType | IAuditType[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AuditTypeService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
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

    it('should create a AuditType', () => {
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

      service.create(new AuditType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AuditType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
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

    it('should partial update a AuditType', () => {
      const patchObject = Object.assign(
        {
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        new AuditType()
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

    it('should return a list of AuditType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
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

    it('should delete a AuditType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAuditTypeToCollectionIfMissing', () => {
      it('should add a AuditType to an empty array', () => {
        const auditType: IAuditType = { id: 123 };
        expectedResult = service.addAuditTypeToCollectionIfMissing([], auditType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(auditType);
      });

      it('should not add a AuditType to an array that contains it', () => {
        const auditType: IAuditType = { id: 123 };
        const auditTypeCollection: IAuditType[] = [
          {
            ...auditType,
          },
          { id: 456 },
        ];
        expectedResult = service.addAuditTypeToCollectionIfMissing(auditTypeCollection, auditType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AuditType to an array that doesn't contain it", () => {
        const auditType: IAuditType = { id: 123 };
        const auditTypeCollection: IAuditType[] = [{ id: 456 }];
        expectedResult = service.addAuditTypeToCollectionIfMissing(auditTypeCollection, auditType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(auditType);
      });

      it('should add only unique AuditType to an array', () => {
        const auditTypeArray: IAuditType[] = [{ id: 123 }, { id: 456 }, { id: 40793 }];
        const auditTypeCollection: IAuditType[] = [{ id: 123 }];
        expectedResult = service.addAuditTypeToCollectionIfMissing(auditTypeCollection, ...auditTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const auditType: IAuditType = { id: 123 };
        const auditType2: IAuditType = { id: 456 };
        expectedResult = service.addAuditTypeToCollectionIfMissing([], auditType, auditType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(auditType);
        expect(expectedResult).toContain(auditType2);
      });

      it('should accept null and undefined values', () => {
        const auditType: IAuditType = { id: 123 };
        expectedResult = service.addAuditTypeToCollectionIfMissing([], null, auditType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(auditType);
      });

      it('should return initial array if no AuditType is added', () => {
        const auditTypeCollection: IAuditType[] = [{ id: 123 }];
        expectedResult = service.addAuditTypeToCollectionIfMissing(auditTypeCollection, undefined, null);
        expect(expectedResult).toEqual(auditTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
