import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IInventoryType, InventoryType } from '../inventory-type.model';

import { InventoryTypeService } from './inventory-type.service';

describe('InventoryType Service', () => {
  let service: InventoryTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: IInventoryType;
  let expectedResult: IInventoryType | IInventoryType[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InventoryTypeService);
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

    it('should create a InventoryType', () => {
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

      service.create(new InventoryType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a InventoryType', () => {
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

    it('should partial update a InventoryType', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          deleted: true,
          lastModifiedBy: 'BBBBBB',
        },
        new InventoryType()
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

    it('should return a list of InventoryType', () => {
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

    it('should delete a InventoryType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addInventoryTypeToCollectionIfMissing', () => {
      it('should add a InventoryType to an empty array', () => {
        const inventoryType: IInventoryType = { id: 123 };
        expectedResult = service.addInventoryTypeToCollectionIfMissing([], inventoryType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(inventoryType);
      });

      it('should not add a InventoryType to an array that contains it', () => {
        const inventoryType: IInventoryType = { id: 123 };
        const inventoryTypeCollection: IInventoryType[] = [
          {
            ...inventoryType,
          },
          { id: 456 },
        ];
        expectedResult = service.addInventoryTypeToCollectionIfMissing(inventoryTypeCollection, inventoryType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a InventoryType to an array that doesn't contain it", () => {
        const inventoryType: IInventoryType = { id: 123 };
        const inventoryTypeCollection: IInventoryType[] = [{ id: 456 }];
        expectedResult = service.addInventoryTypeToCollectionIfMissing(inventoryTypeCollection, inventoryType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(inventoryType);
      });

      it('should add only unique InventoryType to an array', () => {
        const inventoryTypeArray: IInventoryType[] = [{ id: 123 }, { id: 456 }, { id: 55225 }];
        const inventoryTypeCollection: IInventoryType[] = [{ id: 123 }];
        expectedResult = service.addInventoryTypeToCollectionIfMissing(inventoryTypeCollection, ...inventoryTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const inventoryType: IInventoryType = { id: 123 };
        const inventoryType2: IInventoryType = { id: 456 };
        expectedResult = service.addInventoryTypeToCollectionIfMissing([], inventoryType, inventoryType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(inventoryType);
        expect(expectedResult).toContain(inventoryType2);
      });

      it('should accept null and undefined values', () => {
        const inventoryType: IInventoryType = { id: 123 };
        expectedResult = service.addInventoryTypeToCollectionIfMissing([], null, inventoryType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(inventoryType);
      });

      it('should return initial array if no InventoryType is added', () => {
        const inventoryTypeCollection: IInventoryType[] = [{ id: 123 }];
        expectedResult = service.addInventoryTypeToCollectionIfMissing(inventoryTypeCollection, undefined, null);
        expect(expectedResult).toEqual(inventoryTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
