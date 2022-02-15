import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IICMRDailyCount, ICMRDailyCount } from '../icmr-daily-count.model';
import { ICMRDailyCountService } from '../service/icmr-daily-count.service';

import { ICMRDailyCountRoutingResolveService } from './icmr-daily-count-routing-resolve.service';

describe('ICMRDailyCount routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ICMRDailyCountRoutingResolveService;
  let service: ICMRDailyCountService;
  let resultICMRDailyCount: IICMRDailyCount | undefined;

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
    routingResolveService = TestBed.inject(ICMRDailyCountRoutingResolveService);
    service = TestBed.inject(ICMRDailyCountService);
    resultICMRDailyCount = undefined;
  });

  describe('resolve', () => {
    it('should return IICMRDailyCount returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultICMRDailyCount = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultICMRDailyCount).toEqual({ id: 123 });
    });

    it('should return new IICMRDailyCount if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultICMRDailyCount = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultICMRDailyCount).toEqual(new ICMRDailyCount());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ICMRDailyCount })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultICMRDailyCount = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultICMRDailyCount).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
