import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InventoryService } from '../service/inventory.service';
import { IInventory, Inventory } from '../inventory.model';
import { IInventoryMaster } from 'app/entities/inventory-master/inventory-master.model';
import { InventoryMasterService } from 'app/entities/inventory-master/service/inventory-master.service';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { SupplierService } from 'app/entities/supplier/service/supplier.service';
import { IHospital } from 'app/entities/hospital/hospital.model';
import { HospitalService } from 'app/entities/hospital/service/hospital.service';

import { InventoryUpdateComponent } from './inventory-update.component';

describe('Inventory Management Update Component', () => {
  let comp: InventoryUpdateComponent;
  let fixture: ComponentFixture<InventoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let inventoryService: InventoryService;
  let inventoryMasterService: InventoryMasterService;
  let supplierService: SupplierService;
  let hospitalService: HospitalService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InventoryUpdateComponent],
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
      .overrideTemplate(InventoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InventoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    inventoryService = TestBed.inject(InventoryService);
    inventoryMasterService = TestBed.inject(InventoryMasterService);
    supplierService = TestBed.inject(SupplierService);
    hospitalService = TestBed.inject(HospitalService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call InventoryMaster query and add missing value', () => {
      const inventory: IInventory = { id: 456 };
      const inventoryMaster: IInventoryMaster = { id: 89073 };
      inventory.inventoryMaster = inventoryMaster;

      const inventoryMasterCollection: IInventoryMaster[] = [{ id: 41270 }];
      jest.spyOn(inventoryMasterService, 'query').mockReturnValue(of(new HttpResponse({ body: inventoryMasterCollection })));
      const additionalInventoryMasters = [inventoryMaster];
      const expectedCollection: IInventoryMaster[] = [...additionalInventoryMasters, ...inventoryMasterCollection];
      jest.spyOn(inventoryMasterService, 'addInventoryMasterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ inventory });
      comp.ngOnInit();

      expect(inventoryMasterService.query).toHaveBeenCalled();
      expect(inventoryMasterService.addInventoryMasterToCollectionIfMissing).toHaveBeenCalledWith(
        inventoryMasterCollection,
        ...additionalInventoryMasters
      );
      expect(comp.inventoryMastersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Supplier query and add missing value', () => {
      const inventory: IInventory = { id: 456 };
      const supplier: ISupplier = { id: 21617 };
      inventory.supplier = supplier;

      const supplierCollection: ISupplier[] = [{ id: 170 }];
      jest.spyOn(supplierService, 'query').mockReturnValue(of(new HttpResponse({ body: supplierCollection })));
      const additionalSuppliers = [supplier];
      const expectedCollection: ISupplier[] = [...additionalSuppliers, ...supplierCollection];
      jest.spyOn(supplierService, 'addSupplierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ inventory });
      comp.ngOnInit();

      expect(supplierService.query).toHaveBeenCalled();
      expect(supplierService.addSupplierToCollectionIfMissing).toHaveBeenCalledWith(supplierCollection, ...additionalSuppliers);
      expect(comp.suppliersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Hospital query and add missing value', () => {
      const inventory: IInventory = { id: 456 };
      const hospital: IHospital = { id: 57867 };
      inventory.hospital = hospital;

      const hospitalCollection: IHospital[] = [{ id: 25422 }];
      jest.spyOn(hospitalService, 'query').mockReturnValue(of(new HttpResponse({ body: hospitalCollection })));
      const additionalHospitals = [hospital];
      const expectedCollection: IHospital[] = [...additionalHospitals, ...hospitalCollection];
      jest.spyOn(hospitalService, 'addHospitalToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ inventory });
      comp.ngOnInit();

      expect(hospitalService.query).toHaveBeenCalled();
      expect(hospitalService.addHospitalToCollectionIfMissing).toHaveBeenCalledWith(hospitalCollection, ...additionalHospitals);
      expect(comp.hospitalsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const inventory: IInventory = { id: 456 };
      const inventoryMaster: IInventoryMaster = { id: 51359 };
      inventory.inventoryMaster = inventoryMaster;
      const supplier: ISupplier = { id: 3858 };
      inventory.supplier = supplier;
      const hospital: IHospital = { id: 80919 };
      inventory.hospital = hospital;

      activatedRoute.data = of({ inventory });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(inventory));
      expect(comp.inventoryMastersSharedCollection).toContain(inventoryMaster);
      expect(comp.suppliersSharedCollection).toContain(supplier);
      expect(comp.hospitalsSharedCollection).toContain(hospital);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Inventory>>();
      const inventory = { id: 123 };
      jest.spyOn(inventoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inventory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inventory }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(inventoryService.update).toHaveBeenCalledWith(inventory);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Inventory>>();
      const inventory = new Inventory();
      jest.spyOn(inventoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inventory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inventory }));
      saveSubject.complete();

      // THEN
      expect(inventoryService.create).toHaveBeenCalledWith(inventory);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Inventory>>();
      const inventory = { id: 123 };
      jest.spyOn(inventoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inventory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(inventoryService.update).toHaveBeenCalledWith(inventory);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackInventoryMasterById', () => {
      it('Should return tracked InventoryMaster primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackInventoryMasterById(0, entity);
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

    describe('trackHospitalById', () => {
      it('Should return tracked Hospital primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackHospitalById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
