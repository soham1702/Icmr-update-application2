import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAuditSystem, AuditSystem } from '../audit-system.model';

import { AuditSystemService } from './audit-system.service';

describe('AuditSystem Service', () => {
  let service: AuditSystemService;
  let httpMock: HttpTestingController;
  let elemDefault: IAuditSystem;
  let expectedResult: IAuditSystem | IAuditSystem[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AuditSystemService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      auditorName: 'AAAAAAA',
      defectCount: 0,
      defectFixCount: 0,
      inspectionDate: currentDate,
      remark: 'AAAAAAA',
      status: 'AAAAAAA',
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          inspectionDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a AuditSystem', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          inspectionDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          inspectionDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new AuditSystem()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AuditSystem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          auditorName: 'BBBBBB',
          defectCount: 1,
          defectFixCount: 1,
          inspectionDate: currentDate.format(DATE_TIME_FORMAT),
          remark: 'BBBBBB',
          status: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          inspectionDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AuditSystem', () => {
      const patchObject = Object.assign(
        {
          defectCount: 1,
          remark: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        new AuditSystem()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          inspectionDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AuditSystem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          auditorName: 'BBBBBB',
          defectCount: 1,
          defectFixCount: 1,
          inspectionDate: currentDate.format(DATE_TIME_FORMAT),
          remark: 'BBBBBB',
          status: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          inspectionDate: currentDate,
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

    it('should delete a AuditSystem', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAuditSystemToCollectionIfMissing', () => {
      it('should add a AuditSystem to an empty array', () => {
        const auditSystem: IAuditSystem = { id: 123 };
        expectedResult = service.addAuditSystemToCollectionIfMissing([], auditSystem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(auditSystem);
      });

      it('should not add a AuditSystem to an array that contains it', () => {
        const auditSystem: IAuditSystem = { id: 123 };
        const auditSystemCollection: IAuditSystem[] = [
          {
            ...auditSystem,
          },
          { id: 456 },
        ];
        expectedResult = service.addAuditSystemToCollectionIfMissing(auditSystemCollection, auditSystem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AuditSystem to an array that doesn't contain it", () => {
        const auditSystem: IAuditSystem = { id: 123 };
        const auditSystemCollection: IAuditSystem[] = [{ id: 456 }];
        expectedResult = service.addAuditSystemToCollectionIfMissing(auditSystemCollection, auditSystem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(auditSystem);
      });

      it('should add only unique AuditSystem to an array', () => {
        const auditSystemArray: IAuditSystem[] = [{ id: 123 }, { id: 456 }, { id: 6811 }];
        const auditSystemCollection: IAuditSystem[] = [{ id: 123 }];
        expectedResult = service.addAuditSystemToCollectionIfMissing(auditSystemCollection, ...auditSystemArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const auditSystem: IAuditSystem = { id: 123 };
        const auditSystem2: IAuditSystem = { id: 456 };
        expectedResult = service.addAuditSystemToCollectionIfMissing([], auditSystem, auditSystem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(auditSystem);
        expect(expectedResult).toContain(auditSystem2);
      });

      it('should accept null and undefined values', () => {
        const auditSystem: IAuditSystem = { id: 123 };
        expectedResult = service.addAuditSystemToCollectionIfMissing([], null, auditSystem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(auditSystem);
      });

      it('should return initial array if no AuditSystem is added', () => {
        const auditSystemCollection: IAuditSystem[] = [{ id: 123 }];
        expectedResult = service.addAuditSystemToCollectionIfMissing(auditSystemCollection, undefined, null);
        expect(expectedResult).toEqual(auditSystemCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
