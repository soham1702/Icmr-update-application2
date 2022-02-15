import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ContactService } from '../service/contact.service';
import { IContact, Contact } from '../contact.model';
import { IContactType } from 'app/entities/contact-type/contact-type.model';
import { ContactTypeService } from 'app/entities/contact-type/service/contact-type.service';
import { IHospital } from 'app/entities/hospital/hospital.model';
import { HospitalService } from 'app/entities/hospital/service/hospital.service';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { SupplierService } from 'app/entities/supplier/service/supplier.service';

import { ContactUpdateComponent } from './contact-update.component';

describe('Contact Management Update Component', () => {
  let comp: ContactUpdateComponent;
  let fixture: ComponentFixture<ContactUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contactService: ContactService;
  let contactTypeService: ContactTypeService;
  let hospitalService: HospitalService;
  let supplierService: SupplierService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ContactUpdateComponent],
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
      .overrideTemplate(ContactUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContactUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contactService = TestBed.inject(ContactService);
    contactTypeService = TestBed.inject(ContactTypeService);
    hospitalService = TestBed.inject(HospitalService);
    supplierService = TestBed.inject(SupplierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ContactType query and add missing value', () => {
      const contact: IContact = { id: 456 };
      const contactType: IContactType = { id: 98610 };
      contact.contactType = contactType;

      const contactTypeCollection: IContactType[] = [{ id: 36796 }];
      jest.spyOn(contactTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: contactTypeCollection })));
      const additionalContactTypes = [contactType];
      const expectedCollection: IContactType[] = [...additionalContactTypes, ...contactTypeCollection];
      jest.spyOn(contactTypeService, 'addContactTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contact });
      comp.ngOnInit();

      expect(contactTypeService.query).toHaveBeenCalled();
      expect(contactTypeService.addContactTypeToCollectionIfMissing).toHaveBeenCalledWith(contactTypeCollection, ...additionalContactTypes);
      expect(comp.contactTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Hospital query and add missing value', () => {
      const contact: IContact = { id: 456 };
      const hospital: IHospital = { id: 35469 };
      contact.hospital = hospital;

      const hospitalCollection: IHospital[] = [{ id: 84969 }];
      jest.spyOn(hospitalService, 'query').mockReturnValue(of(new HttpResponse({ body: hospitalCollection })));
      const additionalHospitals = [hospital];
      const expectedCollection: IHospital[] = [...additionalHospitals, ...hospitalCollection];
      jest.spyOn(hospitalService, 'addHospitalToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contact });
      comp.ngOnInit();

      expect(hospitalService.query).toHaveBeenCalled();
      expect(hospitalService.addHospitalToCollectionIfMissing).toHaveBeenCalledWith(hospitalCollection, ...additionalHospitals);
      expect(comp.hospitalsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Supplier query and add missing value', () => {
      const contact: IContact = { id: 456 };
      const supplier: ISupplier = { id: 11887 };
      contact.supplier = supplier;

      const supplierCollection: ISupplier[] = [{ id: 10568 }];
      jest.spyOn(supplierService, 'query').mockReturnValue(of(new HttpResponse({ body: supplierCollection })));
      const additionalSuppliers = [supplier];
      const expectedCollection: ISupplier[] = [...additionalSuppliers, ...supplierCollection];
      jest.spyOn(supplierService, 'addSupplierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contact });
      comp.ngOnInit();

      expect(supplierService.query).toHaveBeenCalled();
      expect(supplierService.addSupplierToCollectionIfMissing).toHaveBeenCalledWith(supplierCollection, ...additionalSuppliers);
      expect(comp.suppliersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contact: IContact = { id: 456 };
      const contactType: IContactType = { id: 82086 };
      contact.contactType = contactType;
      const hospital: IHospital = { id: 46094 };
      contact.hospital = hospital;
      const supplier: ISupplier = { id: 37798 };
      contact.supplier = supplier;

      activatedRoute.data = of({ contact });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(contact));
      expect(comp.contactTypesSharedCollection).toContain(contactType);
      expect(comp.hospitalsSharedCollection).toContain(hospital);
      expect(comp.suppliersSharedCollection).toContain(supplier);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Contact>>();
      const contact = { id: 123 };
      jest.spyOn(contactService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contact });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contact }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(contactService.update).toHaveBeenCalledWith(contact);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Contact>>();
      const contact = new Contact();
      jest.spyOn(contactService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contact });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contact }));
      saveSubject.complete();

      // THEN
      expect(contactService.create).toHaveBeenCalledWith(contact);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Contact>>();
      const contact = { id: 123 };
      jest.spyOn(contactService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contact });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contactService.update).toHaveBeenCalledWith(contact);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackContactTypeById', () => {
      it('Should return tracked ContactType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackContactTypeById(0, entity);
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
