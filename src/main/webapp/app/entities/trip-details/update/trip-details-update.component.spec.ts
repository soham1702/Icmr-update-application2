import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TripDetailsService } from '../service/trip-details.service';
import { ITripDetails, TripDetails } from '../trip-details.model';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { SupplierService } from 'app/entities/supplier/service/supplier.service';
import { IHospital } from 'app/entities/hospital/hospital.model';
import { HospitalService } from 'app/entities/hospital/service/hospital.service';
import { ITransactions } from 'app/entities/transactions/transactions.model';
import { TransactionsService } from 'app/entities/transactions/service/transactions.service';
import { ITrip } from 'app/entities/trip/trip.model';
import { TripService } from 'app/entities/trip/service/trip.service';

import { TripDetailsUpdateComponent } from './trip-details-update.component';

describe('TripDetails Management Update Component', () => {
  let comp: TripDetailsUpdateComponent;
  let fixture: ComponentFixture<TripDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tripDetailsService: TripDetailsService;
  let supplierService: SupplierService;
  let hospitalService: HospitalService;
  let transactionsService: TransactionsService;
  let tripService: TripService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TripDetailsUpdateComponent],
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
      .overrideTemplate(TripDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TripDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tripDetailsService = TestBed.inject(TripDetailsService);
    supplierService = TestBed.inject(SupplierService);
    hospitalService = TestBed.inject(HospitalService);
    transactionsService = TestBed.inject(TransactionsService);
    tripService = TestBed.inject(TripService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Supplier query and add missing value', () => {
      const tripDetails: ITripDetails = { id: 456 };
      const supplier: ISupplier = { id: 33964 };
      tripDetails.supplier = supplier;

      const supplierCollection: ISupplier[] = [{ id: 1909 }];
      jest.spyOn(supplierService, 'query').mockReturnValue(of(new HttpResponse({ body: supplierCollection })));
      const additionalSuppliers = [supplier];
      const expectedCollection: ISupplier[] = [...additionalSuppliers, ...supplierCollection];
      jest.spyOn(supplierService, 'addSupplierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tripDetails });
      comp.ngOnInit();

      expect(supplierService.query).toHaveBeenCalled();
      expect(supplierService.addSupplierToCollectionIfMissing).toHaveBeenCalledWith(supplierCollection, ...additionalSuppliers);
      expect(comp.suppliersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Hospital query and add missing value', () => {
      const tripDetails: ITripDetails = { id: 456 };
      const hospital: IHospital = { id: 22469 };
      tripDetails.hospital = hospital;

      const hospitalCollection: IHospital[] = [{ id: 94942 }];
      jest.spyOn(hospitalService, 'query').mockReturnValue(of(new HttpResponse({ body: hospitalCollection })));
      const additionalHospitals = [hospital];
      const expectedCollection: IHospital[] = [...additionalHospitals, ...hospitalCollection];
      jest.spyOn(hospitalService, 'addHospitalToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tripDetails });
      comp.ngOnInit();

      expect(hospitalService.query).toHaveBeenCalled();
      expect(hospitalService.addHospitalToCollectionIfMissing).toHaveBeenCalledWith(hospitalCollection, ...additionalHospitals);
      expect(comp.hospitalsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Transactions query and add missing value', () => {
      const tripDetails: ITripDetails = { id: 456 };
      const transactions: ITransactions = { id: 68724 };
      tripDetails.transactions = transactions;

      const transactionsCollection: ITransactions[] = [{ id: 61236 }];
      jest.spyOn(transactionsService, 'query').mockReturnValue(of(new HttpResponse({ body: transactionsCollection })));
      const additionalTransactions = [transactions];
      const expectedCollection: ITransactions[] = [...additionalTransactions, ...transactionsCollection];
      jest.spyOn(transactionsService, 'addTransactionsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tripDetails });
      comp.ngOnInit();

      expect(transactionsService.query).toHaveBeenCalled();
      expect(transactionsService.addTransactionsToCollectionIfMissing).toHaveBeenCalledWith(
        transactionsCollection,
        ...additionalTransactions
      );
      expect(comp.transactionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Trip query and add missing value', () => {
      const tripDetails: ITripDetails = { id: 456 };
      const trip: ITrip = { id: 23735 };
      tripDetails.trip = trip;

      const tripCollection: ITrip[] = [{ id: 11474 }];
      jest.spyOn(tripService, 'query').mockReturnValue(of(new HttpResponse({ body: tripCollection })));
      const additionalTrips = [trip];
      const expectedCollection: ITrip[] = [...additionalTrips, ...tripCollection];
      jest.spyOn(tripService, 'addTripToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tripDetails });
      comp.ngOnInit();

      expect(tripService.query).toHaveBeenCalled();
      expect(tripService.addTripToCollectionIfMissing).toHaveBeenCalledWith(tripCollection, ...additionalTrips);
      expect(comp.tripsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tripDetails: ITripDetails = { id: 456 };
      const supplier: ISupplier = { id: 76706 };
      tripDetails.supplier = supplier;
      const hospital: IHospital = { id: 73940 };
      tripDetails.hospital = hospital;
      const transactions: ITransactions = { id: 90516 };
      tripDetails.transactions = transactions;
      const trip: ITrip = { id: 51383 };
      tripDetails.trip = trip;

      activatedRoute.data = of({ tripDetails });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tripDetails));
      expect(comp.suppliersSharedCollection).toContain(supplier);
      expect(comp.hospitalsSharedCollection).toContain(hospital);
      expect(comp.transactionsSharedCollection).toContain(transactions);
      expect(comp.tripsSharedCollection).toContain(trip);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TripDetails>>();
      const tripDetails = { id: 123 };
      jest.spyOn(tripDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tripDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tripDetails }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tripDetailsService.update).toHaveBeenCalledWith(tripDetails);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TripDetails>>();
      const tripDetails = new TripDetails();
      jest.spyOn(tripDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tripDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tripDetails }));
      saveSubject.complete();

      // THEN
      expect(tripDetailsService.create).toHaveBeenCalledWith(tripDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TripDetails>>();
      const tripDetails = { id: 123 };
      jest.spyOn(tripDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tripDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tripDetailsService.update).toHaveBeenCalledWith(tripDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
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

    describe('trackTransactionsById', () => {
      it('Should return tracked Transactions primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTransactionsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTripById', () => {
      it('Should return tracked Trip primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTripById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
