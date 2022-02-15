import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BedTypeService } from '../service/bed-type.service';
import { IBedType, BedType } from '../bed-type.model';

import { BedTypeUpdateComponent } from './bed-type-update.component';

describe('BedType Management Update Component', () => {
  let comp: BedTypeUpdateComponent;
  let fixture: ComponentFixture<BedTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bedTypeService: BedTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BedTypeUpdateComponent],
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
      .overrideTemplate(BedTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BedTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bedTypeService = TestBed.inject(BedTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const bedType: IBedType = { id: 456 };

      activatedRoute.data = of({ bedType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(bedType));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BedType>>();
      const bedType = { id: 123 };
      jest.spyOn(bedTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bedType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bedType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(bedTypeService.update).toHaveBeenCalledWith(bedType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BedType>>();
      const bedType = new BedType();
      jest.spyOn(bedTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bedType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bedType }));
      saveSubject.complete();

      // THEN
      expect(bedTypeService.create).toHaveBeenCalledWith(bedType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BedType>>();
      const bedType = { id: 123 };
      jest.spyOn(bedTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bedType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bedTypeService.update).toHaveBeenCalledWith(bedType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
