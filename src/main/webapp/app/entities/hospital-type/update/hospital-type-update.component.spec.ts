import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HospitalTypeService } from '../service/hospital-type.service';
import { IHospitalType, HospitalType } from '../hospital-type.model';

import { HospitalTypeUpdateComponent } from './hospital-type-update.component';

describe('HospitalType Management Update Component', () => {
  let comp: HospitalTypeUpdateComponent;
  let fixture: ComponentFixture<HospitalTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let hospitalTypeService: HospitalTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HospitalTypeUpdateComponent],
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
      .overrideTemplate(HospitalTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HospitalTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    hospitalTypeService = TestBed.inject(HospitalTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const hospitalType: IHospitalType = { id: 456 };

      activatedRoute.data = of({ hospitalType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(hospitalType));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HospitalType>>();
      const hospitalType = { id: 123 };
      jest.spyOn(hospitalTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hospitalType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hospitalType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(hospitalTypeService.update).toHaveBeenCalledWith(hospitalType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HospitalType>>();
      const hospitalType = new HospitalType();
      jest.spyOn(hospitalTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hospitalType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hospitalType }));
      saveSubject.complete();

      // THEN
      expect(hospitalTypeService.create).toHaveBeenCalledWith(hospitalType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HospitalType>>();
      const hospitalType = { id: 123 };
      jest.spyOn(hospitalTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hospitalType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(hospitalTypeService.update).toHaveBeenCalledWith(hospitalType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
