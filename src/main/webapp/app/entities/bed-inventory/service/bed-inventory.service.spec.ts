import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IBedInventory, BedInventory } from '../bed-inventory.model';

import { BedInventoryService } from './bed-inventory.service';

describe('BedInventory Service', () => {
  let service: BedInventoryService;
  let httpMock: HttpTestingController;
  let elemDefault: IBedInventory;
  let expectedResult: IBedInventory | IBedInventory[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BedInventoryService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      bedCount: 0,
      occupied: 0,
      onCylinder: 0,
      onLMO: 0,
      onConcentrators: 0,
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

    it('should create a BedInventory', () => {
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

      service.create(new BedInventory()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BedInventory', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          bedCount: 1,
          occupied: 1,
          onCylinder: 1,
          onLMO: 1,
          onConcentrators: 1,
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

    it('should partial update a BedInventory', () => {
      const patchObject = Object.assign(
        {
          bedCount: 1,
          occupied: 1,
          onLMO: 1,
          lastModifiedBy: 'BBBBBB',
        },
        new BedInventory()
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

    it('should return a list of BedInventory', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          bedCount: 1,
          occupied: 1,
          onCylinder: 1,
          onLMO: 1,
          onConcentrators: 1,
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

    it('should delete a BedInventory', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBedInventoryToCollectionIfMissing', () => {
      it('should add a BedInventory to an empty array', () => {
        const bedInventory: IBedInventory = { id: 123 };
        expectedResult = service.addBedInventoryToCollectionIfMissing([], bedInventory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bedInventory);
      });

      it('should not add a BedInventory to an array that contains it', () => {
        const bedInventory: IBedInventory = { id: 123 };
        const bedInventoryCollection: IBedInventory[] = [
          {
            ...bedInventory,
          },
          { id: 456 },
        ];
        expectedResult = service.addBedInventoryToCollectionIfMissing(bedInventoryCollection, bedInventory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BedInventory to an array that doesn't contain it", () => {
        const bedInventory: IBedInventory = { id: 123 };
        const bedInventoryCollection: IBedInventory[] = [{ id: 456 }];
        expectedResult = service.addBedInventoryToCollectionIfMissing(bedInventoryCollection, bedInventory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bedInventory);
      });

      it('should add only unique BedInventory to an array', () => {
        const bedInventoryArray: IBedInventory[] = [{ id: 123 }, { id: 456 }, { id: 7467 }];
        const bedInventoryCollection: IBedInventory[] = [{ id: 123 }];
        expectedResult = service.addBedInventoryToCollectionIfMissing(bedInventoryCollection, ...bedInventoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bedInventory: IBedInventory = { id: 123 };
        const bedInventory2: IBedInventory = { id: 456 };
        expectedResult = service.addBedInventoryToCollectionIfMissing([], bedInventory, bedInventory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bedInventory);
        expect(expectedResult).toContain(bedInventory2);
      });

      it('should accept null and undefined values', () => {
        const bedInventory: IBedInventory = { id: 123 };
        expectedResult = service.addBedInventoryToCollectionIfMissing([], null, bedInventory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bedInventory);
      });

      it('should return initial array if no BedInventory is added', () => {
        const bedInventoryCollection: IBedInventory[] = [{ id: 123 }];
        expectedResult = service.addBedInventoryToCollectionIfMissing(bedInventoryCollection, undefined, null);
        expect(expectedResult).toEqual(bedInventoryCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
