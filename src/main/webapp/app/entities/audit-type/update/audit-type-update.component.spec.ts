import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AuditTypeService } from '../service/audit-type.service';
import { IAuditType, AuditType } from '../audit-type.model';

import { AuditTypeUpdateComponent } from './audit-type-update.component';

describe('AuditType Management Update Component', () => {
  let comp: AuditTypeUpdateComponent;
  let fixture: ComponentFixture<AuditTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let auditTypeService: AuditTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AuditTypeUpdateComponent],
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
      .overrideTemplate(AuditTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AuditTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    auditTypeService = TestBed.inject(AuditTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const auditType: IAuditType = { id: 456 };

      activatedRoute.data = of({ auditType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(auditType));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AuditType>>();
      const auditType = { id: 123 };
      jest.spyOn(auditTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ auditType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: auditType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(auditTypeService.update).toHaveBeenCalledWith(auditType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AuditType>>();
      const auditType = new AuditType();
      jest.spyOn(auditTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ auditType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: auditType }));
      saveSubject.complete();

      // THEN
      expect(auditTypeService.create).toHaveBeenCalledWith(auditType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AuditType>>();
      const auditType = { id: 123 };
      jest.spyOn(auditTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ auditType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(auditTypeService.update).toHaveBeenCalledWith(auditType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
