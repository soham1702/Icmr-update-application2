import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MunicipalCorpService } from '../service/municipal-corp.service';
import { IMunicipalCorp, MunicipalCorp } from '../municipal-corp.model';
import { IDistrict } from 'app/entities/district/district.model';
import { DistrictService } from 'app/entities/district/service/district.service';

import { MunicipalCorpUpdateComponent } from './municipal-corp-update.component';

describe('MunicipalCorp Management Update Component', () => {
  let comp: MunicipalCorpUpdateComponent;
  let fixture: ComponentFixture<MunicipalCorpUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let municipalCorpService: MunicipalCorpService;
  let districtService: DistrictService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MunicipalCorpUpdateComponent],
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
      .overrideTemplate(MunicipalCorpUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MunicipalCorpUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    municipalCorpService = TestBed.inject(MunicipalCorpService);
    districtService = TestBed.inject(DistrictService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call District query and add missing value', () => {
      const municipalCorp: IMunicipalCorp = { id: 456 };
      const district: IDistrict = { id: 39371 };
      municipalCorp.district = district;

      const districtCollection: IDistrict[] = [{ id: 34119 }];
      jest.spyOn(districtService, 'query').mockReturnValue(of(new HttpResponse({ body: districtCollection })));
      const additionalDistricts = [district];
      const expectedCollection: IDistrict[] = [...additionalDistricts, ...districtCollection];
      jest.spyOn(districtService, 'addDistrictToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ municipalCorp });
      comp.ngOnInit();

      expect(districtService.query).toHaveBeenCalled();
      expect(districtService.addDistrictToCollectionIfMissing).toHaveBeenCalledWith(districtCollection, ...additionalDistricts);
      expect(comp.districtsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const municipalCorp: IMunicipalCorp = { id: 456 };
      const district: IDistrict = { id: 4136 };
      municipalCorp.district = district;

      activatedRoute.data = of({ municipalCorp });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(municipalCorp));
      expect(comp.districtsSharedCollection).toContain(district);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MunicipalCorp>>();
      const municipalCorp = { id: 123 };
      jest.spyOn(municipalCorpService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ municipalCorp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: municipalCorp }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(municipalCorpService.update).toHaveBeenCalledWith(municipalCorp);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MunicipalCorp>>();
      const municipalCorp = new MunicipalCorp();
      jest.spyOn(municipalCorpService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ municipalCorp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: municipalCorp }));
      saveSubject.complete();

      // THEN
      expect(municipalCorpService.create).toHaveBeenCalledWith(municipalCorp);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MunicipalCorp>>();
      const municipalCorp = { id: 123 };
      jest.spyOn(municipalCorpService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ municipalCorp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(municipalCorpService.update).toHaveBeenCalledWith(municipalCorp);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDistrictById', () => {
      it('Should return tracked District primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDistrictById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
