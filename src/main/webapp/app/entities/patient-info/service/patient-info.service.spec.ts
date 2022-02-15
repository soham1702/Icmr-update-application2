import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPatientInfo, PatientInfo } from '../patient-info.model';

import { PatientInfoService } from './patient-info.service';

describe('PatientInfo Service', () => {
  let service: PatientInfoService;
  let httpMock: HttpTestingController;
  let elemDefault: IPatientInfo;
  let expectedResult: IPatientInfo | IPatientInfo[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PatientInfoService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      icmrId: 'AAAAAAA',
      srfId: 'AAAAAAA',
      labName: 'AAAAAAA',
      patientID: 'AAAAAAA',
      patientName: 'AAAAAAA',
      age: 'AAAAAAA',
      ageIn: 'AAAAAAA',
      gender: 'AAAAAAA',
      nationality: 'AAAAAAA',
      address: 'AAAAAAA',
      villageTown: 'AAAAAAA',
      pincode: 'AAAAAAA',
      patientCategory: 'AAAAAAA',
      dateOfSampleCollection: currentDate,
      dateOfSampleReceived: currentDate,
      sampleType: 'AAAAAAA',
      sampleId: 'AAAAAAA',
      underlyingMedicalCondition: 'AAAAAAA',
      hospitalized: 'AAAAAAA',
      hospitalName: 'AAAAAAA',
      hospitalizationDate: currentDate,
      hospitalState: 'AAAAAAA',
      hospitalDistrict: 'AAAAAAA',
      symptomsStatus: 'AAAAAAA',
      symptoms: 'AAAAAAA',
      testingKitUsed: 'AAAAAAA',
      eGeneNGene: 'AAAAAAA',
      ctValueOfEGeneNGene: 'AAAAAAA',
      rdRpSGene: 'AAAAAAA',
      ctValueOfRdRpSGene: 'AAAAAAA',
      oRF1aORF1bNN2Gene: 'AAAAAAA',
      ctValueOfORF1aORF1bNN2Gene: 'AAAAAAA',
      repeatSample: 'AAAAAAA',
      dateOfSampleTested: currentDate,
      entryDate: currentDate,
      confirmationDate: currentDate,
      finalResultSample: 'AAAAAAA',
      remarks: 'AAAAAAA',
      editedOn: currentDate,
      ccmsPullDate: currentDate,
      lastModified: currentDate,
      lastModifiedBy: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateOfSampleCollection: currentDate.format(DATE_TIME_FORMAT),
          dateOfSampleReceived: currentDate.format(DATE_TIME_FORMAT),
          hospitalizationDate: currentDate.format(DATE_TIME_FORMAT),
          dateOfSampleTested: currentDate.format(DATE_TIME_FORMAT),
          entryDate: currentDate.format(DATE_TIME_FORMAT),
          confirmationDate: currentDate.format(DATE_TIME_FORMAT),
          editedOn: currentDate.format(DATE_TIME_FORMAT),
          ccmsPullDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a PatientInfo', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateOfSampleCollection: currentDate.format(DATE_TIME_FORMAT),
          dateOfSampleReceived: currentDate.format(DATE_TIME_FORMAT),
          hospitalizationDate: currentDate.format(DATE_TIME_FORMAT),
          dateOfSampleTested: currentDate.format(DATE_TIME_FORMAT),
          entryDate: currentDate.format(DATE_TIME_FORMAT),
          confirmationDate: currentDate.format(DATE_TIME_FORMAT),
          editedOn: currentDate.format(DATE_TIME_FORMAT),
          ccmsPullDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOfSampleCollection: currentDate,
          dateOfSampleReceived: currentDate,
          hospitalizationDate: currentDate,
          dateOfSampleTested: currentDate,
          entryDate: currentDate,
          confirmationDate: currentDate,
          editedOn: currentDate,
          ccmsPullDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.create(new PatientInfo()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PatientInfo', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          icmrId: 'BBBBBB',
          srfId: 'BBBBBB',
          labName: 'BBBBBB',
          patientID: 'BBBBBB',
          patientName: 'BBBBBB',
          age: 'BBBBBB',
          ageIn: 'BBBBBB',
          gender: 'BBBBBB',
          nationality: 'BBBBBB',
          address: 'BBBBBB',
          villageTown: 'BBBBBB',
          pincode: 'BBBBBB',
          patientCategory: 'BBBBBB',
          dateOfSampleCollection: currentDate.format(DATE_TIME_FORMAT),
          dateOfSampleReceived: currentDate.format(DATE_TIME_FORMAT),
          sampleType: 'BBBBBB',
          sampleId: 'BBBBBB',
          underlyingMedicalCondition: 'BBBBBB',
          hospitalized: 'BBBBBB',
          hospitalName: 'BBBBBB',
          hospitalizationDate: currentDate.format(DATE_TIME_FORMAT),
          hospitalState: 'BBBBBB',
          hospitalDistrict: 'BBBBBB',
          symptomsStatus: 'BBBBBB',
          symptoms: 'BBBBBB',
          testingKitUsed: 'BBBBBB',
          eGeneNGene: 'BBBBBB',
          ctValueOfEGeneNGene: 'BBBBBB',
          rdRpSGene: 'BBBBBB',
          ctValueOfRdRpSGene: 'BBBBBB',
          oRF1aORF1bNN2Gene: 'BBBBBB',
          ctValueOfORF1aORF1bNN2Gene: 'BBBBBB',
          repeatSample: 'BBBBBB',
          dateOfSampleTested: currentDate.format(DATE_TIME_FORMAT),
          entryDate: currentDate.format(DATE_TIME_FORMAT),
          confirmationDate: currentDate.format(DATE_TIME_FORMAT),
          finalResultSample: 'BBBBBB',
          remarks: 'BBBBBB',
          editedOn: currentDate.format(DATE_TIME_FORMAT),
          ccmsPullDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOfSampleCollection: currentDate,
          dateOfSampleReceived: currentDate,
          hospitalizationDate: currentDate,
          dateOfSampleTested: currentDate,
          entryDate: currentDate,
          confirmationDate: currentDate,
          editedOn: currentDate,
          ccmsPullDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PatientInfo', () => {
      const patchObject = Object.assign(
        {
          srfId: 'BBBBBB',
          labName: 'BBBBBB',
          ageIn: 'BBBBBB',
          gender: 'BBBBBB',
          villageTown: 'BBBBBB',
          patientCategory: 'BBBBBB',
          dateOfSampleCollection: currentDate.format(DATE_TIME_FORMAT),
          dateOfSampleReceived: currentDate.format(DATE_TIME_FORMAT),
          sampleType: 'BBBBBB',
          sampleId: 'BBBBBB',
          hospitalName: 'BBBBBB',
          hospitalizationDate: currentDate.format(DATE_TIME_FORMAT),
          hospitalState: 'BBBBBB',
          symptoms: 'BBBBBB',
          testingKitUsed: 'BBBBBB',
          ctValueOfEGeneNGene: 'BBBBBB',
          rdRpSGene: 'BBBBBB',
          ctValueOfRdRpSGene: 'BBBBBB',
          oRF1aORF1bNN2Gene: 'BBBBBB',
          confirmationDate: currentDate.format(DATE_TIME_FORMAT),
          remarks: 'BBBBBB',
          ccmsPullDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
        },
        new PatientInfo()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateOfSampleCollection: currentDate,
          dateOfSampleReceived: currentDate,
          hospitalizationDate: currentDate,
          dateOfSampleTested: currentDate,
          entryDate: currentDate,
          confirmationDate: currentDate,
          editedOn: currentDate,
          ccmsPullDate: currentDate,
          lastModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PatientInfo', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          icmrId: 'BBBBBB',
          srfId: 'BBBBBB',
          labName: 'BBBBBB',
          patientID: 'BBBBBB',
          patientName: 'BBBBBB',
          age: 'BBBBBB',
          ageIn: 'BBBBBB',
          gender: 'BBBBBB',
          nationality: 'BBBBBB',
          address: 'BBBBBB',
          villageTown: 'BBBBBB',
          pincode: 'BBBBBB',
          patientCategory: 'BBBBBB',
          dateOfSampleCollection: currentDate.format(DATE_TIME_FORMAT),
          dateOfSampleReceived: currentDate.format(DATE_TIME_FORMAT),
          sampleType: 'BBBBBB',
          sampleId: 'BBBBBB',
          underlyingMedicalCondition: 'BBBBBB',
          hospitalized: 'BBBBBB',
          hospitalName: 'BBBBBB',
          hospitalizationDate: currentDate.format(DATE_TIME_FORMAT),
          hospitalState: 'BBBBBB',
          hospitalDistrict: 'BBBBBB',
          symptomsStatus: 'BBBBBB',
          symptoms: 'BBBBBB',
          testingKitUsed: 'BBBBBB',
          eGeneNGene: 'BBBBBB',
          ctValueOfEGeneNGene: 'BBBBBB',
          rdRpSGene: 'BBBBBB',
          ctValueOfRdRpSGene: 'BBBBBB',
          oRF1aORF1bNN2Gene: 'BBBBBB',
          ctValueOfORF1aORF1bNN2Gene: 'BBBBBB',
          repeatSample: 'BBBBBB',
          dateOfSampleTested: currentDate.format(DATE_TIME_FORMAT),
          entryDate: currentDate.format(DATE_TIME_FORMAT),
          confirmationDate: currentDate.format(DATE_TIME_FORMAT),
          finalResultSample: 'BBBBBB',
          remarks: 'BBBBBB',
          editedOn: currentDate.format(DATE_TIME_FORMAT),
          ccmsPullDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOfSampleCollection: currentDate,
          dateOfSampleReceived: currentDate,
          hospitalizationDate: currentDate,
          dateOfSampleTested: currentDate,
          entryDate: currentDate,
          confirmationDate: currentDate,
          editedOn: currentDate,
          ccmsPullDate: currentDate,
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

    it('should delete a PatientInfo', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPatientInfoToCollectionIfMissing', () => {
      it('should add a PatientInfo to an empty array', () => {
        const patientInfo: IPatientInfo = { id: 123 };
        expectedResult = service.addPatientInfoToCollectionIfMissing([], patientInfo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(patientInfo);
      });

      it('should not add a PatientInfo to an array that contains it', () => {
        const patientInfo: IPatientInfo = { id: 123 };
        const patientInfoCollection: IPatientInfo[] = [
          {
            ...patientInfo,
          },
          { id: 456 },
        ];
        expectedResult = service.addPatientInfoToCollectionIfMissing(patientInfoCollection, patientInfo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PatientInfo to an array that doesn't contain it", () => {
        const patientInfo: IPatientInfo = { id: 123 };
        const patientInfoCollection: IPatientInfo[] = [{ id: 456 }];
        expectedResult = service.addPatientInfoToCollectionIfMissing(patientInfoCollection, patientInfo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(patientInfo);
      });

      it('should add only unique PatientInfo to an array', () => {
        const patientInfoArray: IPatientInfo[] = [{ id: 123 }, { id: 456 }, { id: 35357 }];
        const patientInfoCollection: IPatientInfo[] = [{ id: 123 }];
        expectedResult = service.addPatientInfoToCollectionIfMissing(patientInfoCollection, ...patientInfoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const patientInfo: IPatientInfo = { id: 123 };
        const patientInfo2: IPatientInfo = { id: 456 };
        expectedResult = service.addPatientInfoToCollectionIfMissing([], patientInfo, patientInfo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(patientInfo);
        expect(expectedResult).toContain(patientInfo2);
      });

      it('should accept null and undefined values', () => {
        const patientInfo: IPatientInfo = { id: 123 };
        expectedResult = service.addPatientInfoToCollectionIfMissing([], null, patientInfo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(patientInfo);
      });

      it('should return initial array if no PatientInfo is added', () => {
        const patientInfoCollection: IPatientInfo[] = [{ id: 123 }];
        expectedResult = service.addPatientInfoToCollectionIfMissing(patientInfoCollection, undefined, null);
        expect(expectedResult).toEqual(patientInfoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
