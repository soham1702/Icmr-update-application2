import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IInventoryMaster, InventoryMaster } from '../inventory-master.model';

import { InventoryMasterService } from './inventory-master.service';

describe('InventoryMaster Service', () => {
  let service: InventoryMasterService;
  let httpMock: HttpTestingController;
  let elemDefault: IInventoryMaster;
  let expectedResult: IInventoryMaster | IInventoryMaster[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InventoryMasterService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      description: 'AAAAAAA',
      volume: 0,
      unit: 'AAAAAAA',
      calulateVolume: 0,
      dimensions: 'AAAAAAA',
      subTypeInd: 'AAAAAAA',
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

    it('should create a InventoryMaster', () => {
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

      service.create(new InventoryMaster()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a InventoryMaster', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          description: 'BBBBBB',
          volume: 1,
          unit: 'BBBBBB',
          calulateVolume: 1,
          dimensions: 'BBBBBB',
          subTypeInd: 'BBBBBB',
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

    it('should partial update a InventoryMaster', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          unit: 'BBBBBB',
          dimensions: 'BBBBBB',
          subTypeInd: 'BBBBBB',
        },
        new InventoryMaster()
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

    it('should return a list of InventoryMaster', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          description: 'BBBBBB',
          volume: 1,
          unit: 'BBBBBB',
          calulateVolume: 1,
          dimensions: 'BBBBBB',
          subTypeInd: 'BBBBBB',
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

    it('should delete a InventoryMaster', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addInventoryMasterToCollectionIfMissing', () => {
      it('should add a InventoryMaster to an empty array', () => {
        const inventoryMaster: IInventoryMaster = { id: 123 };
        expectedResult = service.addInventoryMasterToCollectionIfMissing([], inventoryMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(inventoryMaster);
      });

      it('should not add a InventoryMaster to an array that contains it', () => {
        const inventoryMaster: IInventoryMaster = { id: 123 };
        const inventoryMasterCollection: IInventoryMaster[] = [
          {
            ...inventoryMaster,
          },
          { id: 456 },
        ];
        expectedResult = service.addInventoryMasterToCollectionIfMissing(inventoryMasterCollection, inventoryMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a InventoryMaster to an array that doesn't contain it", () => {
        const inventoryMaster: IInventoryMaster = { id: 123 };
        const inventoryMasterCollection: IInventoryMaster[] = [{ id: 456 }];
        expectedResult = service.addInventoryMasterToCollectionIfMissing(inventoryMasterCollection, inventoryMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(inventoryMaster);
      });

      it('should add only unique InventoryMaster to an array', () => {
        const inventoryMasterArray: IInventoryMaster[] = [{ id: 123 }, { id: 456 }, { id: 77692 }];
        const inventoryMasterCollection: IInventoryMaster[] = [{ id: 123 }];
        expectedResult = service.addInventoryMasterToCollectionIfMissing(inventoryMasterCollection, ...inventoryMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const inventoryMaster: IInventoryMaster = { id: 123 };
        const inventoryMaster2: IInventoryMaster = { id: 456 };
        expectedResult = service.addInventoryMasterToCollectionIfMissing([], inventoryMaster, inventoryMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(inventoryMaster);
        expect(expectedResult).toContain(inventoryMaster2);
      });

      it('should accept null and undefined values', () => {
        const inventoryMaster: IInventoryMaster = { id: 123 };
        expectedResult = service.addInventoryMasterToCollectionIfMissing([], null, inventoryMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(inventoryMaster);
      });

      it('should return initial array if no InventoryMaster is added', () => {
        const inventoryMasterCollection: IInventoryMaster[] = [{ id: 123 }];
        expectedResult = service.addInventoryMasterToCollectionIfMissing(inventoryMasterCollection, undefined, null);
        expect(expectedResult).toEqual(inventoryMasterCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
