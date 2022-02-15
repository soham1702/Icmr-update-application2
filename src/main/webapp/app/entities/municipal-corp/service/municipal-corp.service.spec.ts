import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMunicipalCorp, MunicipalCorp } from '../municipal-corp.model';

import { MunicipalCorpService } from './municipal-corp.service';

describe('MunicipalCorp Service', () => {
  let service: MunicipalCorpService;
  let httpMock: HttpTestingController;
  let elemDefault: IMunicipalCorp;
  let expectedResult: IMunicipalCorp | IMunicipalCorp[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MunicipalCorpService);
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

    it('should create a MunicipalCorp', () => {
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

      service.create(new MunicipalCorp()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MunicipalCorp', () => {
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

    it('should partial update a MunicipalCorp', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        new MunicipalCorp()
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

    it('should return a list of MunicipalCorp', () => {
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

    it('should delete a MunicipalCorp', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMunicipalCorpToCollectionIfMissing', () => {
      it('should add a MunicipalCorp to an empty array', () => {
        const municipalCorp: IMunicipalCorp = { id: 123 };
        expectedResult = service.addMunicipalCorpToCollectionIfMissing([], municipalCorp);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(municipalCorp);
      });

      it('should not add a MunicipalCorp to an array that contains it', () => {
        const municipalCorp: IMunicipalCorp = { id: 123 };
        const municipalCorpCollection: IMunicipalCorp[] = [
          {
            ...municipalCorp,
          },
          { id: 456 },
        ];
        expectedResult = service.addMunicipalCorpToCollectionIfMissing(municipalCorpCollection, municipalCorp);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MunicipalCorp to an array that doesn't contain it", () => {
        const municipalCorp: IMunicipalCorp = { id: 123 };
        const municipalCorpCollection: IMunicipalCorp[] = [{ id: 456 }];
        expectedResult = service.addMunicipalCorpToCollectionIfMissing(municipalCorpCollection, municipalCorp);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(municipalCorp);
      });

      it('should add only unique MunicipalCorp to an array', () => {
        const municipalCorpArray: IMunicipalCorp[] = [{ id: 123 }, { id: 456 }, { id: 44833 }];
        const municipalCorpCollection: IMunicipalCorp[] = [{ id: 123 }];
        expectedResult = service.addMunicipalCorpToCollectionIfMissing(municipalCorpCollection, ...municipalCorpArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const municipalCorp: IMunicipalCorp = { id: 123 };
        const municipalCorp2: IMunicipalCorp = { id: 456 };
        expectedResult = service.addMunicipalCorpToCollectionIfMissing([], municipalCorp, municipalCorp2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(municipalCorp);
        expect(expectedResult).toContain(municipalCorp2);
      });

      it('should accept null and undefined values', () => {
        const municipalCorp: IMunicipalCorp = { id: 123 };
        expectedResult = service.addMunicipalCorpToCollectionIfMissing([], null, municipalCorp, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(municipalCorp);
      });

      it('should return initial array if no MunicipalCorp is added', () => {
        const municipalCorpCollection: IMunicipalCorp[] = [{ id: 123 }];
        expectedResult = service.addMunicipalCorpToCollectionIfMissing(municipalCorpCollection, undefined, null);
        expect(expectedResult).toEqual(municipalCorpCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
