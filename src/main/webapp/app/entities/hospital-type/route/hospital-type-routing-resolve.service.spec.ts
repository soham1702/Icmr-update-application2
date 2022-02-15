import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IHospitalType, HospitalType } from '../hospital-type.model';
import { HospitalTypeService } from '../service/hospital-type.service';

import { HospitalTypeRoutingResolveService } from './hospital-type-routing-resolve.service';

describe('HospitalType routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: HospitalTypeRoutingResolveService;
  let service: HospitalTypeService;
  let resultHospitalType: IHospitalType | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(HospitalTypeRoutingResolveService);
    service = TestBed.inject(HospitalTypeService);
    resultHospitalType = undefined;
  });

  describe('resolve', () => {
    it('should return IHospitalType returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultHospitalType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultHospitalType).toEqual({ id: 123 });
    });

    it('should return new IHospitalType if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultHospitalType = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultHospitalType).toEqual(new HospitalType());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as HospitalType })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultHospitalType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultHospitalType).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
