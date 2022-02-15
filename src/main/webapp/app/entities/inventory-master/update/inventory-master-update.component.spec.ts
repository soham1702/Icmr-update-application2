import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InventoryMasterService } from '../service/inventory-master.service';
import { IInventoryMaster, InventoryMaster } from '../inventory-master.model';
import { IInventoryType } from 'app/entities/inventory-type/inventory-type.model';
import { InventoryTypeService } from 'app/entities/inventory-type/service/inventory-type.service';

import { InventoryMasterUpdateComponent } from './inventory-master-update.component';

describe('InventoryMaster Management Update Component', () => {
  let comp: InventoryMasterUpdateComponent;
  let fixture: ComponentFixture<InventoryMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let inventoryMasterService: InventoryMasterService;
  let inventoryTypeService: InventoryTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InventoryMasterUpdateComponent],
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
      .overrideTemplate(InventoryMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InventoryMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    inventoryMasterService = TestBed.inject(InventoryMasterService);
    inventoryTypeService = TestBed.inject(InventoryTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call InventoryType query and add missing value', () => {
      const inventoryMaster: IInventoryMaster = { id: 456 };
      const inventoryType: IInventoryType = { id: 38951 };
      inventoryMaster.inventoryType = inventoryType;

      const inventoryTypeCollection: IInventoryType[] = [{ id: 56246 }];
      jest.spyOn(inventoryTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: inventoryTypeCollection })));
      const additionalInventoryTypes = [inventoryType];
      const expectedCollection: IInventoryType[] = [...additionalInventoryTypes, ...inventoryTypeCollection];
      jest.spyOn(inventoryTypeService, 'addInventoryTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ inventoryMaster });
      comp.ngOnInit();

      expect(inventoryTypeService.query).toHaveBeenCalled();
      expect(inventoryTypeService.addInventoryTypeToCollectionIfMissing).toHaveBeenCalledWith(
        inventoryTypeCollection,
        ...additionalInventoryTypes
      );
      expect(comp.inventoryTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const inventoryMaster: IInventoryMaster = { id: 456 };
      const inventoryType: IInventoryType = { id: 88821 };
      inventoryMaster.inventoryType = inventoryType;

      activatedRoute.data = of({ inventoryMaster });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(inventoryMaster));
      expect(comp.inventoryTypesSharedCollection).toContain(inventoryType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<InventoryMaster>>();
      const inventoryMaster = { id: 123 };
      jest.spyOn(inventoryMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inventoryMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inventoryMaster }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(inventoryMasterService.update).toHaveBeenCalledWith(inventoryMaster);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<InventoryMaster>>();
      const inventoryMaster = new InventoryMaster();
      jest.spyOn(inventoryMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inventoryMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inventoryMaster }));
      saveSubject.complete();

      // THEN
      expect(inventoryMasterService.create).toHaveBeenCalledWith(inventoryMaster);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<InventoryMaster>>();
      const inventoryMaster = { id: 123 };
      jest.spyOn(inventoryMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inventoryMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(inventoryMasterService.update).toHaveBeenCalledWith(inventoryMaster);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackInventoryTypeById', () => {
      it('Should return tracked InventoryType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackInventoryTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
