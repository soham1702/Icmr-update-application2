import dayjs from 'dayjs/esm';
import { IState } from 'app/entities/state/state.model';
import { IDistrict } from 'app/entities/district/district.model';

export interface IPatientInfo {
  id?: number;
  icmrId?: string;
  srfId?: string | null;
  labName?: string | null;
  patientID?: string | null;
  patientName?: string | null;
  age?: string | null;
  ageIn?: string | null;
  gender?: string | null;
  nationality?: string | null;
  address?: string | null;
  villageTown?: string | null;
  pincode?: string | null;
  patientCategory?: string | null;
  dateOfSampleCollection?: dayjs.Dayjs | null;
  dateOfSampleReceived?: dayjs.Dayjs | null;
  sampleType?: string | null;
  sampleId?: string | null;
  underlyingMedicalCondition?: string | null;
  hospitalized?: string | null;
  hospitalName?: string | null;
  hospitalizationDate?: dayjs.Dayjs | null;
  hospitalState?: string | null;
  hospitalDistrict?: string | null;
  symptomsStatus?: string | null;
  symptoms?: string | null;
  testingKitUsed?: string | null;
  eGeneNGene?: string | null;
  ctValueOfEGeneNGene?: string | null;
  rdRpSGene?: string | null;
  ctValueOfRdRpSGene?: string | null;
  oRF1aORF1bNN2Gene?: string | null;
  ctValueOfORF1aORF1bNN2Gene?: string | null;
  repeatSample?: string | null;
  dateOfSampleTested?: dayjs.Dayjs | null;
  entryDate?: dayjs.Dayjs | null;
  confirmationDate?: dayjs.Dayjs | null;
  finalResultSample?: string | null;
  remarks?: string | null;
  editedOn?: dayjs.Dayjs | null;
  ccmsPullDate?: dayjs.Dayjs;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  state?: IState | null;
  district?: IDistrict | null;
}

export class PatientInfo implements IPatientInfo {
  constructor(
    public id?: number,
    public icmrId?: string,
    public srfId?: string | null,
    public labName?: string | null,
    public patientID?: string | null,
    public patientName?: string | null,
    public age?: string | null,
    public ageIn?: string | null,
    public gender?: string | null,
    public nationality?: string | null,
    public address?: string | null,
    public villageTown?: string | null,
    public pincode?: string | null,
    public patientCategory?: string | null,
    public dateOfSampleCollection?: dayjs.Dayjs | null,
    public dateOfSampleReceived?: dayjs.Dayjs | null,
    public sampleType?: string | null,
    public sampleId?: string | null,
    public underlyingMedicalCondition?: string | null,
    public hospitalized?: string | null,
    public hospitalName?: string | null,
    public hospitalizationDate?: dayjs.Dayjs | null,
    public hospitalState?: string | null,
    public hospitalDistrict?: string | null,
    public symptomsStatus?: string | null,
    public symptoms?: string | null,
    public testingKitUsed?: string | null,
    public eGeneNGene?: string | null,
    public ctValueOfEGeneNGene?: string | null,
    public rdRpSGene?: string | null,
    public ctValueOfRdRpSGene?: string | null,
    public oRF1aORF1bNN2Gene?: string | null,
    public ctValueOfORF1aORF1bNN2Gene?: string | null,
    public repeatSample?: string | null,
    public dateOfSampleTested?: dayjs.Dayjs | null,
    public entryDate?: dayjs.Dayjs | null,
    public confirmationDate?: dayjs.Dayjs | null,
    public finalResultSample?: string | null,
    public remarks?: string | null,
    public editedOn?: dayjs.Dayjs | null,
    public ccmsPullDate?: dayjs.Dayjs,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public state?: IState | null,
    public district?: IDistrict | null
  ) {}
}

export function getPatientInfoIdentifier(patientInfo: IPatientInfo): number | undefined {
  return patientInfo.id;
}
