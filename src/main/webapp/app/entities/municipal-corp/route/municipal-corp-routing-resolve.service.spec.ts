import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IMunicipalCorp, MunicipalCorp } from '../municipal-corp.model';
import { MunicipalCorpService } from '../service/municipal-corp.service';

import { MunicipalCorpRoutingResolveService } from './municipal-corp-routing-resolve.service';

describe('MunicipalCorp routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: MunicipalCorpRoutingResolveService;
  let service: MunicipalCorpService;
  let resultMunicipalCorp: IMunicipalCorp | undefined;

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
    routingResolveService = TestBed.inject(MunicipalCorpRoutingResolveService);
    service = TestBed.inject(MunicipalCorpService);
    resultMunicipalCorp = undefined;
  });

  describe('resolve', () => {
    it('should return IMunicipalCorp returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMunicipalCorp = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMunicipalCorp).toEqual({ id: 123 });
    });

    it('should return new IMunicipalCorp if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMunicipalCorp = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultMunicipalCorp).toEqual(new MunicipalCorp());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as MunicipalCorp })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMunicipalCorp = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMunicipalCorp).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
