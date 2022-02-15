import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ContactTypeService } from '../service/contact-type.service';
import { IContactType, ContactType } from '../contact-type.model';

import { ContactTypeUpdateComponent } from './contact-type-update.component';

describe('ContactType Management Update Component', () => {
  let comp: ContactTypeUpdateComponent;
  let fixture: ComponentFixture<ContactTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contactTypeService: ContactTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ContactTypeUpdateComponent],
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
      .overrideTemplate(ContactTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContactTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contactTypeService = TestBed.inject(ContactTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const contactType: IContactType = { id: 456 };

      activatedRoute.data = of({ contactType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(contactType));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ContactType>>();
      const contactType = { id: 123 };
      jest.spyOn(contactTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contactType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contactType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(contactTypeService.update).toHaveBeenCalledWith(contactType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ContactType>>();
      const contactType = new ContactType();
      jest.spyOn(contactTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contactType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contactType }));
      saveSubject.complete();

      // THEN
      expect(contactTypeService.create).toHaveBeenCalledWith(contactType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ContactType>>();
      const contactType = { id: 123 };
      jest.spyOn(contactTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contactType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contactTypeService.update).toHaveBeenCalledWith(contactType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
