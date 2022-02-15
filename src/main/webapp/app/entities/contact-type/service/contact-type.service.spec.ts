import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IContactType, ContactType } from '../contact-type.model';

import { ContactTypeService } from './contact-type.service';

describe('ContactType Service', () => {
  let service: ContactTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: IContactType;
  let expectedResult: IContactType | IContactType[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ContactTypeService);
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

    it('should create a ContactType', () => {
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

      service.create(new ContactType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ContactType', () => {
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

    it('should partial update a ContactType', () => {
      const patchObject = Object.assign(
        {
          deleted: true,
          lastModifiedBy: 'BBBBBB',
        },
        new ContactType()
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

    it('should return a list of ContactType', () => {
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

    it('should delete a ContactType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addContactTypeToCollectionIfMissing', () => {
      it('should add a ContactType to an empty array', () => {
        const contactType: IContactType = { id: 123 };
        expectedResult = service.addContactTypeToCollectionIfMissing([], contactType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contactType);
      });

      it('should not add a ContactType to an array that contains it', () => {
        const contactType: IContactType = { id: 123 };
        const contactTypeCollection: IContactType[] = [
          {
            ...contactType,
          },
          { id: 456 },
        ];
        expectedResult = service.addContactTypeToCollectionIfMissing(contactTypeCollection, contactType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ContactType to an array that doesn't contain it", () => {
        const contactType: IContactType = { id: 123 };
        const contactTypeCollection: IContactType[] = [{ id: 456 }];
        expectedResult = service.addContactTypeToCollectionIfMissing(contactTypeCollection, contactType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contactType);
      });

      it('should add only unique ContactType to an array', () => {
        const contactTypeArray: IContactType[] = [{ id: 123 }, { id: 456 }, { id: 98182 }];
        const contactTypeCollection: IContactType[] = [{ id: 123 }];
        expectedResult = service.addContactTypeToCollectionIfMissing(contactTypeCollection, ...contactTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const contactType: IContactType = { id: 123 };
        const contactType2: IContactType = { id: 456 };
        expectedResult = service.addContactTypeToCollectionIfMissing([], contactType, contactType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contactType);
        expect(expectedResult).toContain(contactType2);
      });

      it('should accept null and undefined values', () => {
        const contactType: IContactType = { id: 123 };
        expectedResult = service.addContactTypeToCollectionIfMissing([], null, contactType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contactType);
      });

      it('should return initial array if no ContactType is added', () => {
        const contactTypeCollection: IContactType[] = [{ id: 123 }];
        expectedResult = service.addContactTypeToCollectionIfMissing(contactTypeCollection, undefined, null);
        expect(expectedResult).toEqual(contactTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
