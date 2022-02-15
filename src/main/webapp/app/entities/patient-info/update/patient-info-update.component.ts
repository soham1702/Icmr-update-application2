import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPatientInfo, PatientInfo } from '../patient-info.model';
import { PatientInfoService } from '../service/patient-info.service';
import { IState } from 'app/entities/state/state.model';
import { StateService } from 'app/entities/state/service/state.service';
import { IDistrict } from 'app/entities/district/district.model';
import { DistrictService } from 'app/entities/district/service/district.service';

@Component({
  selector: 'jhi-patient-info-update',
  templateUrl: './patient-info-update.component.html',
})
export class PatientInfoUpdateComponent implements OnInit {
  isSaving = false;

  statesSharedCollection: IState[] = [];
  districtsSharedCollection: IDistrict[] = [];

  editForm = this.fb.group({
    id: [],
    icmrId: [null, [Validators.required]],
    srfId: [],
    labName: [],
    patientID: [],
    patientName: [],
    age: [],
    ageIn: [],
    gender: [],
    nationality: [],
    address: [],
    villageTown: [],
    pincode: [],
    patientCategory: [],
    dateOfSampleCollection: [],
    dateOfSampleReceived: [],
    sampleType: [],
    sampleId: [],
    underlyingMedicalCondition: [],
    hospitalized: [],
    hospitalName: [],
    hospitalizationDate: [],
    hospitalState: [],
    hospitalDistrict: [],
    symptomsStatus: [],
    symptoms: [],
    testingKitUsed: [],
    eGeneNGene: [],
    ctValueOfEGeneNGene: [],
    rdRpSGene: [],
    ctValueOfRdRpSGene: [],
    oRF1aORF1bNN2Gene: [],
    ctValueOfORF1aORF1bNN2Gene: [],
    repeatSample: [],
    dateOfSampleTested: [],
    entryDate: [],
    confirmationDate: [],
    finalResultSample: [],
    remarks: [],
    editedOn: [],
    ccmsPullDate: [null, [Validators.required]],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    state: [],
    district: [],
  });

  constructor(
    protected patientInfoService: PatientInfoService,
    protected stateService: StateService,
    protected districtService: DistrictService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ patientInfo }) => {
      if (patientInfo.id === undefined) {
        const today = dayjs().startOf('day');
        patientInfo.dateOfSampleCollection = today;
        patientInfo.dateOfSampleReceived = today;
        patientInfo.hospitalizationDate = today;
        patientInfo.dateOfSampleTested = today;
        patientInfo.entryDate = today;
        patientInfo.confirmationDate = today;
        patientInfo.editedOn = today;
        patientInfo.ccmsPullDate = today;
        patientInfo.lastModified = today;
      }

      this.updateForm(patientInfo);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const patientInfo = this.createFromForm();
    if (patientInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.patientInfoService.update(patientInfo));
    } else {
      this.subscribeToSaveResponse(this.patientInfoService.create(patientInfo));
    }
  }

  trackStateById(index: number, item: IState): number {
    return item.id!;
  }

  trackDistrictById(index: number, item: IDistrict): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatientInfo>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(patientInfo: IPatientInfo): void {
    this.editForm.patchValue({
      id: patientInfo.id,
      icmrId: patientInfo.icmrId,
      srfId: patientInfo.srfId,
      labName: patientInfo.labName,
      patientID: patientInfo.patientID,
      patientName: patientInfo.patientName,
      age: patientInfo.age,
      ageIn: patientInfo.ageIn,
      gender: patientInfo.gender,
      nationality: patientInfo.nationality,
      address: patientInfo.address,
      villageTown: patientInfo.villageTown,
      pincode: patientInfo.pincode,
      patientCategory: patientInfo.patientCategory,
      dateOfSampleCollection: patientInfo.dateOfSampleCollection ? patientInfo.dateOfSampleCollection.format(DATE_TIME_FORMAT) : null,
      dateOfSampleReceived: patientInfo.dateOfSampleReceived ? patientInfo.dateOfSampleReceived.format(DATE_TIME_FORMAT) : null,
      sampleType: patientInfo.sampleType,
      sampleId: patientInfo.sampleId,
      underlyingMedicalCondition: patientInfo.underlyingMedicalCondition,
      hospitalized: patientInfo.hospitalized,
      hospitalName: patientInfo.hospitalName,
      hospitalizationDate: patientInfo.hospitalizationDate ? patientInfo.hospitalizationDate.format(DATE_TIME_FORMAT) : null,
      hospitalState: patientInfo.hospitalState,
      hospitalDistrict: patientInfo.hospitalDistrict,
      symptomsStatus: patientInfo.symptomsStatus,
      symptoms: patientInfo.symptoms,
      testingKitUsed: patientInfo.testingKitUsed,
      eGeneNGene: patientInfo.eGeneNGene,
      ctValueOfEGeneNGene: patientInfo.ctValueOfEGeneNGene,
      rdRpSGene: patientInfo.rdRpSGene,
      ctValueOfRdRpSGene: patientInfo.ctValueOfRdRpSGene,
      oRF1aORF1bNN2Gene: patientInfo.oRF1aORF1bNN2Gene,
      ctValueOfORF1aORF1bNN2Gene: patientInfo.ctValueOfORF1aORF1bNN2Gene,
      repeatSample: patientInfo.repeatSample,
      dateOfSampleTested: patientInfo.dateOfSampleTested ? patientInfo.dateOfSampleTested.format(DATE_TIME_FORMAT) : null,
      entryDate: patientInfo.entryDate ? patientInfo.entryDate.format(DATE_TIME_FORMAT) : null,
      confirmationDate: patientInfo.confirmationDate ? patientInfo.confirmationDate.format(DATE_TIME_FORMAT) : null,
      finalResultSample: patientInfo.finalResultSample,
      remarks: patientInfo.remarks,
      editedOn: patientInfo.editedOn ? patientInfo.editedOn.format(DATE_TIME_FORMAT) : null,
      ccmsPullDate: patientInfo.ccmsPullDate ? patientInfo.ccmsPullDate.format(DATE_TIME_FORMAT) : null,
      lastModified: patientInfo.lastModified ? patientInfo.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: patientInfo.lastModifiedBy,
      state: patientInfo.state,
      district: patientInfo.district,
    });

    this.statesSharedCollection = this.stateService.addStateToCollectionIfMissing(this.statesSharedCollection, patientInfo.state);
    this.districtsSharedCollection = this.districtService.addDistrictToCollectionIfMissing(
      this.districtsSharedCollection,
      patientInfo.district
    );
  }

  protected loadRelationshipsOptions(): void {
    this.stateService
      .query()
      .pipe(map((res: HttpResponse<IState[]>) => res.body ?? []))
      .pipe(map((states: IState[]) => this.stateService.addStateToCollectionIfMissing(states, this.editForm.get('state')!.value)))
      .subscribe((states: IState[]) => (this.statesSharedCollection = states));

    this.districtService
      .query()
      .pipe(map((res: HttpResponse<IDistrict[]>) => res.body ?? []))
      .pipe(
        map((districts: IDistrict[]) =>
          this.districtService.addDistrictToCollectionIfMissing(districts, this.editForm.get('district')!.value)
        )
      )
      .subscribe((districts: IDistrict[]) => (this.districtsSharedCollection = districts));
  }

  protected createFromForm(): IPatientInfo {
    return {
      ...new PatientInfo(),
      id: this.editForm.get(['id'])!.value,
      icmrId: this.editForm.get(['icmrId'])!.value,
      srfId: this.editForm.get(['srfId'])!.value,
      labName: this.editForm.get(['labName'])!.value,
      patientID: this.editForm.get(['patientID'])!.value,
      patientName: this.editForm.get(['patientName'])!.value,
      age: this.editForm.get(['age'])!.value,
      ageIn: this.editForm.get(['ageIn'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      nationality: this.editForm.get(['nationality'])!.value,
      address: this.editForm.get(['address'])!.value,
      villageTown: this.editForm.get(['villageTown'])!.value,
      pincode: this.editForm.get(['pincode'])!.value,
      patientCategory: this.editForm.get(['patientCategory'])!.value,
      dateOfSampleCollection: this.editForm.get(['dateOfSampleCollection'])!.value
        ? dayjs(this.editForm.get(['dateOfSampleCollection'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dateOfSampleReceived: this.editForm.get(['dateOfSampleReceived'])!.value
        ? dayjs(this.editForm.get(['dateOfSampleReceived'])!.value, DATE_TIME_FORMAT)
        : undefined,
      sampleType: this.editForm.get(['sampleType'])!.value,
      sampleId: this.editForm.get(['sampleId'])!.value,
      underlyingMedicalCondition: this.editForm.get(['underlyingMedicalCondition'])!.value,
      hospitalized: this.editForm.get(['hospitalized'])!.value,
      hospitalName: this.editForm.get(['hospitalName'])!.value,
      hospitalizationDate: this.editForm.get(['hospitalizationDate'])!.value
        ? dayjs(this.editForm.get(['hospitalizationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      hospitalState: this.editForm.get(['hospitalState'])!.value,
      hospitalDistrict: this.editForm.get(['hospitalDistrict'])!.value,
      symptomsStatus: this.editForm.get(['symptomsStatus'])!.value,
      symptoms: this.editForm.get(['symptoms'])!.value,
      testingKitUsed: this.editForm.get(['testingKitUsed'])!.value,
      eGeneNGene: this.editForm.get(['eGeneNGene'])!.value,
      ctValueOfEGeneNGene: this.editForm.get(['ctValueOfEGeneNGene'])!.value,
      rdRpSGene: this.editForm.get(['rdRpSGene'])!.value,
      ctValueOfRdRpSGene: this.editForm.get(['ctValueOfRdRpSGene'])!.value,
      oRF1aORF1bNN2Gene: this.editForm.get(['oRF1aORF1bNN2Gene'])!.value,
      ctValueOfORF1aORF1bNN2Gene: this.editForm.get(['ctValueOfORF1aORF1bNN2Gene'])!.value,
      repeatSample: this.editForm.get(['repeatSample'])!.value,
      dateOfSampleTested: this.editForm.get(['dateOfSampleTested'])!.value
        ? dayjs(this.editForm.get(['dateOfSampleTested'])!.value, DATE_TIME_FORMAT)
        : undefined,
      entryDate: this.editForm.get(['entryDate'])!.value ? dayjs(this.editForm.get(['entryDate'])!.value, DATE_TIME_FORMAT) : undefined,
      confirmationDate: this.editForm.get(['confirmationDate'])!.value
        ? dayjs(this.editForm.get(['confirmationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      finalResultSample: this.editForm.get(['finalResultSample'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      editedOn: this.editForm.get(['editedOn'])!.value ? dayjs(this.editForm.get(['editedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      ccmsPullDate: this.editForm.get(['ccmsPullDate'])!.value
        ? dayjs(this.editForm.get(['ccmsPullDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      state: this.editForm.get(['state'])!.value,
      district: this.editForm.get(['district'])!.value,
    };
  }
}
