import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BedTransactionsService } from '../service/bed-transactions.service';
import { IBedTransactions, BedTransactions } from '../bed-transactions.model';
import { IBedType } from 'app/entities/bed-type/bed-type.model';
import { BedTypeService } from 'app/entities/bed-type/service/bed-type.service';
import { IHospital } from 'app/entities/hospital/hospital.model';
import { HospitalService } from 'app/entities/hospital/service/hospital.service';

import { BedTransactionsUpdateComponent } from './bed-transactions-update.component';

describe('BedTransactions Management Update Component', () => {
  let comp: BedTransactionsUpdateComponent;
  let fixture: ComponentFixture<BedTransactionsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bedTransactionsService: BedTransactionsService;
  let bedTypeService: BedTypeService;
  let hospitalService: HospitalService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BedTransactionsUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(BedTransactionsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BedTransactionsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bedTransactionsService = TestBed.inject(BedTransactionsService);
    bedTypeService = TestBed.inject(BedTypeService);
    hospitalService = TestBed.inject(HospitalService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call BedType query and add missing value', () => {
      const bedTransactions: IBedTransactions = { id: 456 };
      const bedType: IBedType = { id: 40951 };
      bedTransactions.bedType = bedType;

      const bedTypeCollection: IBedType[] = [{ id: 90592 }];
      jest.spyOn(bedTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: bedTypeCollection })));
      const additionalBedTypes = [bedType];
      const expectedCollection: IBedType[] = [...additionalBedTypes, ...bedTypeCollection];
      jest.spyOn(bedTypeService, 'addBedTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bedTransactions });
      comp.ngOnInit();

      expect(bedTypeService.query).toHaveBeenCalled();
      expect(bedTypeService.addBedTypeToCollectionIfMissing).toHaveBeenCalledWith(bedTypeCollection, ...additionalBedTypes);
      expect(comp.bedTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Hospital query and add missing value', () => {
      const bedTransactions: IBedTransactions = { id: 456 };
      const hospital: IHospital = { id: 87456 };
      bedTransactions.hospital = hospital;

      const hospitalCollection: IHospital[] = [{ id: 87576 }];
      jest.spyOn(hospitalService, 'query').mockReturnValue(of(new HttpResponse({ body: hospitalCollection })));
      const additionalHospitals = [hospital];
      const expectedCollection: IHospital[] = [...additionalHospitals, ...hospitalCollection];
      jest.spyOn(hospitalService, 'addHospitalToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bedTransactions });
      comp.ngOnInit();

      expect(hospitalService.query).toHaveBeenCalled();
      expect(hospitalService.addHospitalToCollectionIfMissing).toHaveBeenCalledWith(hospitalCollection, ...additionalHospitals);
      expect(comp.hospitalsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bedTransactions: IBedTransactions = { id: 456 };
      const bedType: IBedType = { id: 63472 };
      bedTransactions.bedType = bedType;
      const hospital: IHospital = { id: 78796 };
      bedTransactions.hospital = hospital;

      activatedRoute.data = of({ bedTransactions });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(bedTransactions));
      expect(comp.bedTypesSharedCollection).toContain(bedType);
      expect(comp.hospitalsSharedCollection).toContain(hospital);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BedTransactions>>();
      const bedTransactions = { id: 123 };
      jest.spyOn(bedTransactionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bedTransactions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bedTransactions }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(bedTransactionsService.update).toHaveBeenCalledWith(bedTransactions);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BedTransactions>>();
      const bedTransactions = new BedTransactions();
      jest.spyOn(bedTransactionsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bedTransactions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bedTransactions }));
      saveSubject.complete();

      // THEN
      expect(bedTransactionsService.create).toHaveBeenCalledWith(bedTransactions);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BedTransactions>>();
      const bedTransactions = { id: 123 };
      jest.spyOn(bedTransactionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bedTransactions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bedTransactionsService.update).toHaveBeenCalledWith(bedTransactions);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackBedTypeById', () => {
      it('Should return tracked BedType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackBedTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackHospitalById', () => {
      it('Should return tracked Hospital primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackHospitalById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
