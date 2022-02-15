import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAuditSystem, AuditSystem } from '../audit-system.model';
import { AuditSystemService } from '../service/audit-system.service';

import { AuditSystemRoutingResolveService } from './audit-system-routing-resolve.service';

describe('AuditSystem routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AuditSystemRoutingResolveService;
  let service: AuditSystemService;
  let resultAuditSystem: IAuditSystem | undefined;

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
    routingResolveService = TestBed.inject(AuditSystemRoutingResolveService);
    service = TestBed.inject(AuditSystemService);
    resultAuditSystem = undefined;
  });

  describe('resolve', () => {
    it('should return IAuditSystem returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAuditSystem = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAuditSystem).toEqual({ id: 123 });
    });

    it('should return new IAuditSystem if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAuditSystem = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAuditSystem).toEqual(new AuditSystem());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AuditSystem })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAuditSystem = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAuditSystem).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
