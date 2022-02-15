import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InventoryTypeService } from '../service/inventory-type.service';
import { IInventoryType, InventoryType } from '../inventory-type.model';

import { InventoryTypeUpdateComponent } from './inventory-type-update.component';

describe('InventoryType Management Update Component', () => {
  let comp: InventoryTypeUpdateComponent;
  let fixture: ComponentFixture<InventoryTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let inventoryTypeService: InventoryTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InventoryTypeUpdateComponent],
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
      .overrideTemplate(InventoryTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InventoryTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    inventoryTypeService = TestBed.inject(InventoryTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const inventoryType: IInventoryType = { id: 456 };

      activatedRoute.data = of({ inventoryType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(inventoryType));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<InventoryType>>();
      const inventoryType = { id: 123 };
      jest.spyOn(inventoryTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inventoryType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inventoryType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(inventoryTypeService.update).toHaveBeenCalledWith(inventoryType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<InventoryType>>();
      const inventoryType = new InventoryType();
      jest.spyOn(inventoryTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inventoryType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inventoryType }));
      saveSubject.complete();

      // THEN
      expect(inventoryTypeService.create).toHaveBeenCalledWith(inventoryType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<InventoryType>>();
      const inventoryType = { id: 123 };
      jest.spyOn(inventoryTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inventoryType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(inventoryTypeService.update).toHaveBeenCalledWith(inventoryType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
