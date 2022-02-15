import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICMRDailyCountService } from '../service/icmr-daily-count.service';
import { IICMRDailyCount, ICMRDailyCount } from '../icmr-daily-count.model';

import { ICMRDailyCountUpdateComponent } from './icmr-daily-count-update.component';

describe('ICMRDailyCount Management Update Component', () => {
  let comp: ICMRDailyCountUpdateComponent;
  let fixture: ComponentFixture<ICMRDailyCountUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let iCMRDailyCountService: ICMRDailyCountService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ICMRDailyCountUpdateComponent],
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
      .overrideTemplate(ICMRDailyCountUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ICMRDailyCountUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    iCMRDailyCountService = TestBed.inject(ICMRDailyCountService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const iCMRDailyCount: IICMRDailyCount = { id: 456 };

      activatedRoute.data = of({ iCMRDailyCount });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(iCMRDailyCount));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICMRDailyCount>>();
      const iCMRDailyCount = { id: 123 };
      jest.spyOn(iCMRDailyCountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ iCMRDailyCount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: iCMRDailyCount }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(iCMRDailyCountService.update).toHaveBeenCalledWith(iCMRDailyCount);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICMRDailyCount>>();
      const iCMRDailyCount = new ICMRDailyCount();
      jest.spyOn(iCMRDailyCountService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ iCMRDailyCount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: iCMRDailyCount }));
      saveSubject.complete();

      // THEN
      expect(iCMRDailyCountService.create).toHaveBeenCalledWith(iCMRDailyCount);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICMRDailyCount>>();
      const iCMRDailyCount = { id: 123 };
      jest.spyOn(iCMRDailyCountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ iCMRDailyCount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(iCMRDailyCountService.update).toHaveBeenCalledWith(iCMRDailyCount);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
