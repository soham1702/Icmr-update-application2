import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IInventoryUsed, InventoryUsed } from '../inventory-used.model';

import { InventoryUsedService } from './inventory-used.service';

describe('InventoryUsed Service', () => {
  let service: InventoryUsedService;
  let httpMock: HttpTestingController;
  let elemDefault: IInventoryUsed;
  let expectedResult: IInventoryUsed | IInventoryUsed[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InventoryUsedService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      stock: 0,
      capcity: 0,
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

    it('should create a InventoryUsed', () => {
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

      service.create(new InventoryUsed()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a InventoryUsed', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          stock: 1,
          capcity: 1,
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

    it('should partial update a InventoryUsed', () => {
      const patchObject = Object.assign(
        {
          capcity: 1,
          comment: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        new InventoryUsed()
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

    it('should return a list of InventoryUsed', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          stock: 1,
          capcity: 1,
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

    it('should delete a InventoryUsed', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addInventoryUsedToCollectionIfMissing', () => {
      it('should add a InventoryUsed to an empty array', () => {
        const inventoryUsed: IInventoryUsed = { id: 123 };
        expectedResult = service.addInventoryUsedToCollectionIfMissing([], inventoryUsed);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(inventoryUsed);
      });

      it('should not add a InventoryUsed to an array that contains it', () => {
        const inventoryUsed: IInventoryUsed = { id: 123 };
        const inventoryUsedCollection: IInventoryUsed[] = [
          {
            ...inventoryUsed,
          },
          { id: 456 },
        ];
        expectedResult = service.addInventoryUsedToCollectionIfMissing(inventoryUsedCollection, inventoryUsed);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a InventoryUsed to an array that doesn't contain it", () => {
        const inventoryUsed: IInventoryUsed = { id: 123 };
        const inventoryUsedCollection: IInventoryUsed[] = [{ id: 456 }];
        expectedResult = service.addInventoryUsedToCollectionIfMissing(inventoryUsedCollection, inventoryUsed);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(inventoryUsed);
      });

      it('should add only unique InventoryUsed to an array', () => {
        const inventoryUsedArray: IInventoryUsed[] = [{ id: 123 }, { id: 456 }, { id: 34105 }];
        const inventoryUsedCollection: IInventoryUsed[] = [{ id: 123 }];
        expectedResult = service.addInventoryUsedToCollectionIfMissing(inventoryUsedCollection, ...inventoryUsedArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const inventoryUsed: IInventoryUsed = { id: 123 };
        const inventoryUsed2: IInventoryUsed = { id: 456 };
        expectedResult = service.addInventoryUsedToCollectionIfMissing([], inventoryUsed, inventoryUsed2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(inventoryUsed);
        expect(expectedResult).toContain(inventoryUsed2);
      });

      it('should accept null and undefined values', () => {
        const inventoryUsed: IInventoryUsed = { id: 123 };
        expectedResult = service.addInventoryUsedToCollectionIfMissing([], null, inventoryUsed, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(inventoryUsed);
      });

      it('should return initial array if no InventoryUsed is added', () => {
        const inventoryUsedCollection: IInventoryUsed[] = [{ id: 123 }];
        expectedResult = service.addInventoryUsedToCollectionIfMissing(inventoryUsedCollection, undefined, null);
        expect(expectedResult).toEqual(inventoryUsedCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
