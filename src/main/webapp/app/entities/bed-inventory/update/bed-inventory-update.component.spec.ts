import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BedInventoryService } from '../service/bed-inventory.service';
import { IBedInventory, BedInventory } from '../bed-inventory.model';
import { IBedType } from 'app/entities/bed-type/bed-type.model';
import { BedTypeService } from 'app/entities/bed-type/service/bed-type.service';
import { IHospital } from 'app/entities/hospital/hospital.model';
import { HospitalService } from 'app/entities/hospital/service/hospital.service';

import { BedInventoryUpdateComponent } from './bed-inventory-update.component';

describe('BedInventory Management Update Component', () => {
  let comp: BedInventoryUpdateComponent;
  let fixture: ComponentFixture<BedInventoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bedInventoryService: BedInventoryService;
  let bedTypeService: BedTypeService;
  let hospitalService: HospitalService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BedInventoryUpdateComponent],
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
      .overrideTemplate(BedInventoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BedInventoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bedInventoryService = TestBed.inject(BedInventoryService);
    bedTypeService = TestBed.inject(BedTypeService);
    hospitalService = TestBed.inject(HospitalService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call BedType query and add missing value', () => {
      const bedInventory: IBedInventory = { id: 456 };
      const bedType: IBedType = { id: 34896 };
      bedInventory.bedType = bedType;

      const bedTypeCollection: IBedType[] = [{ id: 69141 }];
      jest.spyOn(bedTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: bedTypeCollection })));
      const additionalBedTypes = [bedType];
      const expectedCollection: IBedType[] = [...additionalBedTypes, ...bedTypeCollection];
      jest.spyOn(bedTypeService, 'addBedTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bedInventory });
      comp.ngOnInit();

      expect(bedTypeService.query).toHaveBeenCalled();
      expect(bedTypeService.addBedTypeToCollectionIfMissing).toHaveBeenCalledWith(bedTypeCollection, ...additionalBedTypes);
      expect(comp.bedTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Hospital query and add missing value', () => {
      const bedInventory: IBedInventory = { id: 456 };
      const hospital: IHospital = { id: 55047 };
      bedInventory.hospital = hospital;

      const hospitalCollection: IHospital[] = [{ id: 98676 }];
      jest.spyOn(hospitalService, 'query').mockReturnValue(of(new HttpResponse({ body: hospitalCollection })));
      const additionalHospitals = [hospital];
      const expectedCollection: IHospital[] = [...additionalHospitals, ...hospitalCollection];
      jest.spyOn(hospitalService, 'addHospitalToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bedInventory });
      comp.ngOnInit();

      expect(hospitalService.query).toHaveBeenCalled();
      expect(hospitalService.addHospitalToCollectionIfMissing).toHaveBeenCalledWith(hospitalCollection, ...additionalHospitals);
      expect(comp.hospitalsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bedInventory: IBedInventory = { id: 456 };
      const bedType: IBedType = { id: 27431 };
      bedInventory.bedType = bedType;
      const hospital: IHospital = { id: 50494 };
      bedInventory.hospital = hospital;

      activatedRoute.data = of({ bedInventory });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(bedInventory));
      expect(comp.bedTypesSharedCollection).toContain(bedType);
      expect(comp.hospitalsSharedCollection).toContain(hospital);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BedInventory>>();
      const bedInventory = { id: 123 };
      jest.spyOn(bedInventoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bedInventory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bedInventory }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(bedInventoryService.update).toHaveBeenCalledWith(bedInventory);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BedInventory>>();
      const bedInventory = new BedInventory();
      jest.spyOn(bedInventoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bedInventory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bedInventory }));
      saveSubject.complete();

      // THEN
      expect(bedInventoryService.create).toHaveBeenCalledWith(bedInventory);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BedInventory>>();
      const bedInventory = { id: 123 };
      jest.spyOn(bedInventoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bedInventory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bedInventoryService.update).toHaveBeenCalledWith(bedInventory);
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
