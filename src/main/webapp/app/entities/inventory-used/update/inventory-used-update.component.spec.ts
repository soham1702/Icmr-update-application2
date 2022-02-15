import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InventoryUsedService } from '../service/inventory-used.service';
import { IInventoryUsed, InventoryUsed } from '../inventory-used.model';
import { IInventory } from 'app/entities/inventory/inventory.model';
import { InventoryService } from 'app/entities/inventory/service/inventory.service';

import { InventoryUsedUpdateComponent } from './inventory-used-update.component';

describe('InventoryUsed Management Update Component', () => {
  let comp: InventoryUsedUpdateComponent;
  let fixture: ComponentFixture<InventoryUsedUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let inventoryUsedService: InventoryUsedService;
  let inventoryService: InventoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InventoryUsedUpdateComponent],
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
      .overrideTemplate(InventoryUsedUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InventoryUsedUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    inventoryUsedService = TestBed.inject(InventoryUsedService);
    inventoryService = TestBed.inject(InventoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Inventory query and add missing value', () => {
      const inventoryUsed: IInventoryUsed = { id: 456 };
      const inventory: IInventory = { id: 64799 };
      inventoryUsed.inventory = inventory;

      const inventoryCollection: IInventory[] = [{ id: 76678 }];
      jest.spyOn(inventoryService, 'query').mockReturnValue(of(new HttpResponse({ body: inventoryCollection })));
      const additionalInventories = [inventory];
      const expectedCollection: IInventory[] = [...additionalInventories, ...inventoryCollection];
      jest.spyOn(inventoryService, 'addInventoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ inventoryUsed });
      comp.ngOnInit();

      expect(inventoryService.query).toHaveBeenCalled();
      expect(inventoryService.addInventoryToCollectionIfMissing).toHaveBeenCalledWith(inventoryCollection, ...additionalInventories);
      expect(comp.inventoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const inventoryUsed: IInventoryUsed = { id: 456 };
      const inventory: IInventory = { id: 51741 };
      inventoryUsed.inventory = inventory;

      activatedRoute.data = of({ inventoryUsed });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(inventoryUsed));
      expect(comp.inventoriesSharedCollection).toContain(inventory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<InventoryUsed>>();
      const inventoryUsed = { id: 123 };
      jest.spyOn(inventoryUsedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inventoryUsed });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inventoryUsed }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(inventoryUsedService.update).toHaveBeenCalledWith(inventoryUsed);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<InventoryUsed>>();
      const inventoryUsed = new InventoryUsed();
      jest.spyOn(inventoryUsedService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inventoryUsed });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inventoryUsed }));
      saveSubject.complete();

      // THEN
      expect(inventoryUsedService.create).toHaveBeenCalledWith(inventoryUsed);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<InventoryUsed>>();
      const inventoryUsed = { id: 123 };
      jest.spyOn(inventoryUsedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inventoryUsed });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(inventoryUsedService.update).toHaveBeenCalledWith(inventoryUsed);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackInventoryById', () => {
      it('Should return tracked Inventory primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackInventoryById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
