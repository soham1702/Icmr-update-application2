import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITaluka, Taluka } from '../taluka.model';

import { TalukaService } from './taluka.service';

describe('Taluka Service', () => {
  let service: TalukaService;
  let httpMock: HttpTestingController;
  let elemDefault: ITaluka;
  let expectedResult: ITaluka | ITaluka[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TalukaService);
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

    it('should create a Taluka', () => {
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

      service.create(new Taluka()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Taluka', () => {
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

    it('should partial update a Taluka', () => {
      const patchObject = Object.assign(
        {
          deleted: true,
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        new Taluka()
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

    it('should return a list of Taluka', () => {
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

    it('should delete a Taluka', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTalukaToCollectionIfMissing', () => {
      it('should add a Taluka to an empty array', () => {
        const taluka: ITaluka = { id: 123 };
        expectedResult = service.addTalukaToCollectionIfMissing([], taluka);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(taluka);
      });

      it('should not add a Taluka to an array that contains it', () => {
        const taluka: ITaluka = { id: 123 };
        const talukaCollection: ITaluka[] = [
          {
            ...taluka,
          },
          { id: 456 },
        ];
        expectedResult = service.addTalukaToCollectionIfMissing(talukaCollection, taluka);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Taluka to an array that doesn't contain it", () => {
        const taluka: ITaluka = { id: 123 };
        const talukaCollection: ITaluka[] = [{ id: 456 }];
        expectedResult = service.addTalukaToCollectionIfMissing(talukaCollection, taluka);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(taluka);
      });

      it('should add only unique Taluka to an array', () => {
        const talukaArray: ITaluka[] = [{ id: 123 }, { id: 456 }, { id: 44946 }];
        const talukaCollection: ITaluka[] = [{ id: 123 }];
        expectedResult = service.addTalukaToCollectionIfMissing(talukaCollection, ...talukaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const taluka: ITaluka = { id: 123 };
        const taluka2: ITaluka = { id: 456 };
        expectedResult = service.addTalukaToCollectionIfMissing([], taluka, taluka2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(taluka);
        expect(expectedResult).toContain(taluka2);
      });

      it('should accept null and undefined values', () => {
        const taluka: ITaluka = { id: 123 };
        expectedResult = service.addTalukaToCollectionIfMissing([], null, taluka, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(taluka);
      });

      it('should return initial array if no Taluka is added', () => {
        const talukaCollection: ITaluka[] = [{ id: 123 }];
        expectedResult = service.addTalukaToCollectionIfMissing(talukaCollection, undefined, null);
        expect(expectedResult).toEqual(talukaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
