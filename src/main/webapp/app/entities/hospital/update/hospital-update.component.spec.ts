import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HospitalService } from '../service/hospital.service';
import { IHospital, Hospital } from '../hospital.model';
import { IState } from 'app/entities/state/state.model';
import { StateService } from 'app/entities/state/service/state.service';
import { IDistrict } from 'app/entities/district/district.model';
import { DistrictService } from 'app/entities/district/service/district.service';
import { ITaluka } from 'app/entities/taluka/taluka.model';
import { TalukaService } from 'app/entities/taluka/service/taluka.service';
import { ICity } from 'app/entities/city/city.model';
import { CityService } from 'app/entities/city/service/city.service';
import { IMunicipalCorp } from 'app/entities/municipal-corp/municipal-corp.model';
import { MunicipalCorpService } from 'app/entities/municipal-corp/service/municipal-corp.service';
import { IHospitalType } from 'app/entities/hospital-type/hospital-type.model';
import { HospitalTypeService } from 'app/entities/hospital-type/service/hospital-type.service';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { SupplierService } from 'app/entities/supplier/service/supplier.service';

import { HospitalUpdateComponent } from './hospital-update.component';

describe('Hospital Management Update Component', () => {
  let comp: HospitalUpdateComponent;
  let fixture: ComponentFixture<HospitalUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let hospitalService: HospitalService;
  let stateService: StateService;
  let districtService: DistrictService;
  let talukaService: TalukaService;
  let cityService: CityService;
  let municipalCorpService: MunicipalCorpService;
  let hospitalTypeService: HospitalTypeService;
  let supplierService: SupplierService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HospitalUpdateComponent],
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
      .overrideTemplate(HospitalUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HospitalUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    hospitalService = TestBed.inject(HospitalService);
    stateService = TestBed.inject(StateService);
    districtService = TestBed.inject(DistrictService);
    talukaService = TestBed.inject(TalukaService);
    cityService = TestBed.inject(CityService);
    municipalCorpService = TestBed.inject(MunicipalCorpService);
    hospitalTypeService = TestBed.inject(HospitalTypeService);
    supplierService = TestBed.inject(SupplierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call State query and add missing value', () => {
      const hospital: IHospital = { id: 456 };
      const state: IState = { id: 15381 };
      hospital.state = state;

      const stateCollection: IState[] = [{ id: 65688 }];
      jest.spyOn(stateService, 'query').mockReturnValue(of(new HttpResponse({ body: stateCollection })));
      const additionalStates = [state];
      const expectedCollection: IState[] = [...additionalStates, ...stateCollection];
      jest.spyOn(stateService, 'addStateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hospital });
      comp.ngOnInit();

      expect(stateService.query).toHaveBeenCalled();
      expect(stateService.addStateToCollectionIfMissing).toHaveBeenCalledWith(stateCollection, ...additionalStates);
      expect(comp.statesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call District query and add missing value', () => {
      const hospital: IHospital = { id: 456 };
      const district: IDistrict = { id: 48930 };
      hospital.district = district;

      const districtCollection: IDistrict[] = [{ id: 48330 }];
      jest.spyOn(districtService, 'query').mockReturnValue(of(new HttpResponse({ body: districtCollection })));
      const additionalDistricts = [district];
      const expectedCollection: IDistrict[] = [...additionalDistricts, ...districtCollection];
      jest.spyOn(districtService, 'addDistrictToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hospital });
      comp.ngOnInit();

      expect(districtService.query).toHaveBeenCalled();
      expect(districtService.addDistrictToCollectionIfMissing).toHaveBeenCalledWith(districtCollection, ...additionalDistricts);
      expect(comp.districtsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Taluka query and add missing value', () => {
      const hospital: IHospital = { id: 456 };
      const taluka: ITaluka = { id: 86158 };
      hospital.taluka = taluka;

      const talukaCollection: ITaluka[] = [{ id: 49364 }];
      jest.spyOn(talukaService, 'query').mockReturnValue(of(new HttpResponse({ body: talukaCollection })));
      const additionalTalukas = [taluka];
      const expectedCollection: ITaluka[] = [...additionalTalukas, ...talukaCollection];
      jest.spyOn(talukaService, 'addTalukaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hospital });
      comp.ngOnInit();

      expect(talukaService.query).toHaveBeenCalled();
      expect(talukaService.addTalukaToCollectionIfMissing).toHaveBeenCalledWith(talukaCollection, ...additionalTalukas);
      expect(comp.talukasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call City query and add missing value', () => {
      const hospital: IHospital = { id: 456 };
      const city: ICity = { id: 7082 };
      hospital.city = city;

      const cityCollection: ICity[] = [{ id: 44656 }];
      jest.spyOn(cityService, 'query').mockReturnValue(of(new HttpResponse({ body: cityCollection })));
      const additionalCities = [city];
      const expectedCollection: ICity[] = [...additionalCities, ...cityCollection];
      jest.spyOn(cityService, 'addCityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hospital });
      comp.ngOnInit();

      expect(cityService.query).toHaveBeenCalled();
      expect(cityService.addCityToCollectionIfMissing).toHaveBeenCalledWith(cityCollection, ...additionalCities);
      expect(comp.citiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call MunicipalCorp query and add missing value', () => {
      const hospital: IHospital = { id: 456 };
      const municipalCorp: IMunicipalCorp = { id: 30778 };
      hospital.municipalCorp = municipalCorp;

      const municipalCorpCollection: IMunicipalCorp[] = [{ id: 5851 }];
      jest.spyOn(municipalCorpService, 'query').mockReturnValue(of(new HttpResponse({ body: municipalCorpCollection })));
      const additionalMunicipalCorps = [municipalCorp];
      const expectedCollection: IMunicipalCorp[] = [...additionalMunicipalCorps, ...municipalCorpCollection];
      jest.spyOn(municipalCorpService, 'addMunicipalCorpToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hospital });
      comp.ngOnInit();

      expect(municipalCorpService.query).toHaveBeenCalled();
      expect(municipalCorpService.addMunicipalCorpToCollectionIfMissing).toHaveBeenCalledWith(
        municipalCorpCollection,
        ...additionalMunicipalCorps
      );
      expect(comp.municipalCorpsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call HospitalType query and add missing value', () => {
      const hospital: IHospital = { id: 456 };
      const hospitalType: IHospitalType = { id: 2550 };
      hospital.hospitalType = hospitalType;

      const hospitalTypeCollection: IHospitalType[] = [{ id: 90780 }];
      jest.spyOn(hospitalTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: hospitalTypeCollection })));
      const additionalHospitalTypes = [hospitalType];
      const expectedCollection: IHospitalType[] = [...additionalHospitalTypes, ...hospitalTypeCollection];
      jest.spyOn(hospitalTypeService, 'addHospitalTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hospital });
      comp.ngOnInit();

      expect(hospitalTypeService.query).toHaveBeenCalled();
      expect(hospitalTypeService.addHospitalTypeToCollectionIfMissing).toHaveBeenCalledWith(
        hospitalTypeCollection,
        ...additionalHospitalTypes
      );
      expect(comp.hospitalTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Supplier query and add missing value', () => {
      const hospital: IHospital = { id: 456 };
      const suppliers: ISupplier[] = [{ id: 16639 }];
      hospital.suppliers = suppliers;

      const supplierCollection: ISupplier[] = [{ id: 11119 }];
      jest.spyOn(supplierService, 'query').mockReturnValue(of(new HttpResponse({ body: supplierCollection })));
      const additionalSuppliers = [...suppliers];
      const expectedCollection: ISupplier[] = [...additionalSuppliers, ...supplierCollection];
      jest.spyOn(supplierService, 'addSupplierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hospital });
      comp.ngOnInit();

      expect(supplierService.query).toHaveBeenCalled();
      expect(supplierService.addSupplierToCollectionIfMissing).toHaveBeenCalledWith(supplierCollection, ...additionalSuppliers);
      expect(comp.suppliersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const hospital: IHospital = { id: 456 };
      const state: IState = { id: 49458 };
      hospital.state = state;
      const district: IDistrict = { id: 38628 };
      hospital.district = district;
      const taluka: ITaluka = { id: 50086 };
      hospital.taluka = taluka;
      const city: ICity = { id: 94138 };
      hospital.city = city;
      const municipalCorp: IMunicipalCorp = { id: 56127 };
      hospital.municipalCorp = municipalCorp;
      const hospitalType: IHospitalType = { id: 96461 };
      hospital.hospitalType = hospitalType;
      const suppliers: ISupplier = { id: 98943 };
      hospital.suppliers = [suppliers];

      activatedRoute.data = of({ hospital });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(hospital));
      expect(comp.statesSharedCollection).toContain(state);
      expect(comp.districtsSharedCollection).toContain(district);
      expect(comp.talukasSharedCollection).toContain(taluka);
      expect(comp.citiesSharedCollection).toContain(city);
      expect(comp.municipalCorpsSharedCollection).toContain(municipalCorp);
      expect(comp.hospitalTypesSharedCollection).toContain(hospitalType);
      expect(comp.suppliersSharedCollection).toContain(suppliers);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Hospital>>();
      const hospital = { id: 123 };
      jest.spyOn(hospitalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hospital });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hospital }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(hospitalService.update).toHaveBeenCalledWith(hospital);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Hospital>>();
      const hospital = new Hospital();
      jest.spyOn(hospitalService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hospital });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hospital }));
      saveSubject.complete();

      // THEN
      expect(hospitalService.create).toHaveBeenCalledWith(hospital);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Hospital>>();
      const hospital = { id: 123 };
      jest.spyOn(hospitalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hospital });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(hospitalService.update).toHaveBeenCalledWith(hospital);
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

    describe('trackMunicipalCorpById', () => {
      it('Should return tracked MunicipalCorp primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackMunicipalCorpById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackHospitalTypeById', () => {
      it('Should return tracked HospitalType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackHospitalTypeById(0, entity);
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

  describe('Getting selected relationships', () => {
    describe('getSelectedSupplier', () => {
      it('Should return option if no Supplier is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedSupplier(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected Supplier for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedSupplier(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this Supplier is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedSupplier(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
