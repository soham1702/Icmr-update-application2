import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SupplierService } from '../service/supplier.service';
import { ISupplier, Supplier } from '../supplier.model';
import { IState } from 'app/entities/state/state.model';
import { StateService } from 'app/entities/state/service/state.service';
import { IDistrict } from 'app/entities/district/district.model';
import { DistrictService } from 'app/entities/district/service/district.service';
import { ITaluka } from 'app/entities/taluka/taluka.model';
import { TalukaService } from 'app/entities/taluka/service/taluka.service';
import { ICity } from 'app/entities/city/city.model';
import { CityService } from 'app/entities/city/service/city.service';
import { IInventoryType } from 'app/entities/inventory-type/inventory-type.model';
import { InventoryTypeService } from 'app/entities/inventory-type/service/inventory-type.service';

import { SupplierUpdateComponent } from './supplier-update.component';

describe('Supplier Management Update Component', () => {
  let comp: SupplierUpdateComponent;
  let fixture: ComponentFixture<SupplierUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let supplierService: SupplierService;
  let stateService: StateService;
  let districtService: DistrictService;
  let talukaService: TalukaService;
  let cityService: CityService;
  let inventoryTypeService: InventoryTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SupplierUpdateComponent],
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
      .overrideTemplate(SupplierUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SupplierUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    supplierService = TestBed.inject(SupplierService);
    stateService = TestBed.inject(StateService);
    districtService = TestBed.inject(DistrictService);
    talukaService = TestBed.inject(TalukaService);
    cityService = TestBed.inject(CityService);
    inventoryTypeService = TestBed.inject(InventoryTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call State query and add missing value', () => {
      const supplier: ISupplier = { id: 456 };
      const state: IState = { id: 49423 };
      supplier.state = state;

      const stateCollection: IState[] = [{ id: 70322 }];
      jest.spyOn(stateService, 'query').mockReturnValue(of(new HttpResponse({ body: stateCollection })));
      const additionalStates = [state];
      const expectedCollection: IState[] = [...additionalStates, ...stateCollection];
      jest.spyOn(stateService, 'addStateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ supplier });
      comp.ngOnInit();

      expect(stateService.query).toHaveBeenCalled();
      expect(stateService.addStateToCollectionIfMissing).toHaveBeenCalledWith(stateCollection, ...additionalStates);
      expect(comp.statesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call District query and add missing value', () => {
      const supplier: ISupplier = { id: 456 };
      const district: IDistrict = { id: 88362 };
      supplier.district = district;

      const districtCollection: IDistrict[] = [{ id: 47442 }];
      jest.spyOn(districtService, 'query').mockReturnValue(of(new HttpResponse({ body: districtCollection })));
      const additionalDistricts = [district];
      const expectedCollection: IDistrict[] = [...additionalDistricts, ...districtCollection];
      jest.spyOn(districtService, 'addDistrictToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ supplier });
      comp.ngOnInit();

      expect(districtService.query).toHaveBeenCalled();
      expect(districtService.addDistrictToCollectionIfMissing).toHaveBeenCalledWith(districtCollection, ...additionalDistricts);
      expect(comp.districtsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Taluka query and add missing value', () => {
      const supplier: ISupplier = { id: 456 };
      const taluka: ITaluka = { id: 67093 };
      supplier.taluka = taluka;

      const talukaCollection: ITaluka[] = [{ id: 26558 }];
      jest.spyOn(talukaService, 'query').mockReturnValue(of(new HttpResponse({ body: talukaCollection })));
      const additionalTalukas = [taluka];
      const expectedCollection: ITaluka[] = [...additionalTalukas, ...talukaCollection];
      jest.spyOn(talukaService, 'addTalukaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ supplier });
      comp.ngOnInit();

      expect(talukaService.query).toHaveBeenCalled();
      expect(talukaService.addTalukaToCollectionIfMissing).toHaveBeenCalledWith(talukaCollection, ...additionalTalukas);
      expect(comp.talukasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call City query and add missing value', () => {
      const supplier: ISupplier = { id: 456 };
      const city: ICity = { id: 44686 };
      supplier.city = city;

      const cityCollection: ICity[] = [{ id: 76865 }];
      jest.spyOn(cityService, 'query').mockReturnValue(of(new HttpResponse({ body: cityCollection })));
      const additionalCities = [city];
      const expectedCollection: ICity[] = [...additionalCities, ...cityCollection];
      jest.spyOn(cityService, 'addCityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ supplier });
      comp.ngOnInit();

      expect(cityService.query).toHaveBeenCalled();
      expect(cityService.addCityToCollectionIfMissing).toHaveBeenCalledWith(cityCollection, ...additionalCities);
      expect(comp.citiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call InventoryType query and add missing value', () => {
      const supplier: ISupplier = { id: 456 };
      const inventoryType: IInventoryType = { id: 45711 };
      supplier.inventoryType = inventoryType;

      const inventoryTypeCollection: IInventoryType[] = [{ id: 75564 }];
      jest.spyOn(inventoryTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: inventoryTypeCollection })));
      const additionalInventoryTypes = [inventoryType];
      const expectedCollection: IInventoryType[] = [...additionalInventoryTypes, ...inventoryTypeCollection];
      jest.spyOn(inventoryTypeService, 'addInventoryTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ supplier });
      comp.ngOnInit();

      expect(inventoryTypeService.query).toHaveBeenCalled();
      expect(inventoryTypeService.addInventoryTypeToCollectionIfMissing).toHaveBeenCalledWith(
        inventoryTypeCollection,
        ...additionalInventoryTypes
      );
      expect(comp.inventoryTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const supplier: ISupplier = { id: 456 };
      const state: IState = { id: 68087 };
      supplier.state = state;
      const district: IDistrict = { id: 2811 };
      supplier.district = district;
      const taluka: ITaluka = { id: 19900 };
      supplier.taluka = taluka;
      const city: ICity = { id: 9320 };
      supplier.city = city;
      const inventoryType: IInventoryType = { id: 46361 };
      supplier.inventoryType = inventoryType;

      activatedRoute.data = of({ supplier });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(supplier));
      expect(comp.statesSharedCollection).toContain(state);
      expect(comp.districtsSharedCollection).toContain(district);
      expect(comp.talukasSharedCollection).toContain(taluka);
      expect(comp.citiesSharedCollection).toContain(city);
      expect(comp.inventoryTypesSharedCollection).toContain(inventoryType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Supplier>>();
      const supplier = { id: 123 };
      jest.spyOn(supplierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ supplier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: supplier }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(supplierService.update).toHaveBeenCalledWith(supplier);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Supplier>>();
      const supplier = new Supplier();
      jest.spyOn(supplierService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ supplier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: supplier }));
      saveSubject.complete();

      // THEN
      expect(supplierService.create).toHaveBeenCalledWith(supplier);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Supplier>>();
      const supplier = { id: 123 };
      jest.spyOn(supplierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ supplier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(supplierService.update).toHaveBeenCalledWith(supplier);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackStateById', () => {
      it('Should return tracked State primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackStateById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDistrictById', () => {
      it('Should return tracked District primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDistrictById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTalukaById', () => {
      it('Should return tracked Taluka primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTalukaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCityById', () => {
      it('Should return tracked City primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCityById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackInventoryTypeById', () => {
      it('Should return tracked InventoryType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackInventoryTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
