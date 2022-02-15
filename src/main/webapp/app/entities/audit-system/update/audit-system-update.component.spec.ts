import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AuditSystemService } from '../service/audit-system.service';
import { IAuditSystem, AuditSystem } from '../audit-system.model';
import { IAuditType } from 'app/entities/audit-type/audit-type.model';
import { AuditTypeService } from 'app/entities/audit-type/service/audit-type.service';
import { IHospital } from 'app/entities/hospital/hospital.model';
import { HospitalService } from 'app/entities/hospital/service/hospital.service';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { SupplierService } from 'app/entities/supplier/service/supplier.service';

import { AuditSystemUpdateComponent } from './audit-system-update.component';

describe('AuditSystem Management Update Component', () => {
  let comp: AuditSystemUpdateComponent;
  let fixture: ComponentFixture<AuditSystemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let auditSystemService: AuditSystemService;
  let auditTypeService: AuditTypeService;
  let hospitalService: HospitalService;
  let supplierService: SupplierService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AuditSystemUpdateComponent],
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
      .overrideTemplate(AuditSystemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AuditSystemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    auditSystemService = TestBed.inject(AuditSystemService);
    auditTypeService = TestBed.inject(AuditTypeService);
    hospitalService = TestBed.inject(HospitalService);
    supplierService = TestBed.inject(SupplierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AuditType query and add missing value', () => {
      const auditSystem: IAuditSystem = { id: 456 };
      const auditType: IAuditType = { id: 99612 };
      auditSystem.auditType = auditType;

      const auditTypeCollection: IAuditType[] = [{ id: 53307 }];
      jest.spyOn(auditTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: auditTypeCollection })));
      const additionalAuditTypes = [auditType];
      const expectedCollection: IAuditType[] = [...additionalAuditTypes, ...auditTypeCollection];
      jest.spyOn(auditTypeService, 'addAuditTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ auditSystem });
      comp.ngOnInit();

      expect(auditTypeService.query).toHaveBeenCalled();
      expect(auditTypeService.addAuditTypeToCollectionIfMissing).toHaveBeenCalledWith(auditTypeCollection, ...additionalAuditTypes);
      expect(comp.auditTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Hospital query and add missing value', () => {
      const auditSystem: IAuditSystem = { id: 456 };
      const hospital: IHospital = { id: 61201 };
      auditSystem.hospital = hospital;

      const hospitalCollection: IHospital[] = [{ id: 37204 }];
      jest.spyOn(hospitalService, 'query').mockReturnValue(of(new HttpResponse({ body: hospitalCollection })));
      const additionalHospitals = [hospital];
      const expectedCollection: IHospital[] = [...additionalHospitals, ...hospitalCollection];
      jest.spyOn(hospitalService, 'addHospitalToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ auditSystem });
      comp.ngOnInit();

      expect(hospitalService.query).toHaveBeenCalled();
      expect(hospitalService.addHospitalToCollectionIfMissing).toHaveBeenCalledWith(hospitalCollection, ...additionalHospitals);
      expect(comp.hospitalsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Supplier query and add missing value', () => {
      const auditSystem: IAuditSystem = { id: 456 };
      const supplier: ISupplier = { id: 67055 };
      auditSystem.supplier = supplier;

      const supplierCollection: ISupplier[] = [{ id: 8750 }];
      jest.spyOn(supplierService, 'query').mockReturnValue(of(new HttpResponse({ body: supplierCollection })));
      const additionalSuppliers = [supplier];
      const expectedCollection: ISupplier[] = [...additionalSuppliers, ...supplierCollection];
      jest.spyOn(supplierService, 'addSupplierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ auditSystem });
      comp.ngOnInit();

      expect(supplierService.query).toHaveBeenCalled();
      expect(supplierService.addSupplierToCollectionIfMissing).toHaveBeenCalledWith(supplierCollection, ...additionalSuppliers);
      expect(comp.suppliersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const auditSystem: IAuditSystem = { id: 456 };
      const auditType: IAuditType = { id: 68800 };
      auditSystem.auditType = auditType;
      const hospital: IHospital = { id: 53266 };
      auditSystem.hospital = hospital;
      const supplier: ISupplier = { id: 66412 };
      auditSystem.supplier = supplier;

      activatedRoute.data = of({ auditSystem });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(auditSystem));
      expect(comp.auditTypesSharedCollection).toContain(auditType);
      expect(comp.hospitalsSharedCollection).toContain(hospital);
      expect(comp.suppliersSharedCollection).toContain(supplier);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AuditSystem>>();
      const auditSystem = { id: 123 };
      jest.spyOn(auditSystemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ auditSystem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: auditSystem }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(auditSystemService.update).toHaveBeenCalledWith(auditSystem);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AuditSystem>>();
      const auditSystem = new AuditSystem();
      jest.spyOn(auditSystemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ auditSystem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: auditSystem }));
      saveSubject.complete();

      // THEN
      expect(auditSystemService.create).toHaveBeenCalledWith(auditSystem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AuditSystem>>();
      const auditSystem = { id: 123 };
      jest.spyOn(auditSystemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ auditSystem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(auditSystemService.update).toHaveBeenCalledWith(auditSystem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAuditTypeById', () => {
      it('Should return tracked AuditType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAuditTypeById(0, entity);
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

    describe('trackSupplierById', () => {
      it('Should return tracked Supplier primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSupplierById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
