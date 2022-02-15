import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IInventoryUsed, InventoryUsed } from '../inventory-used.model';
import { InventoryUsedService } from '../service/inventory-used.service';

import { InventoryUsedRoutingResolveService } from './inventory-used-routing-resolve.service';

describe('InventoryUsed routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: InventoryUsedRoutingResolveService;
  let service: InventoryUsedService;
  let resultInventoryUsed: IInventoryUsed | undefined;

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
    routingResolveService = TestBed.inject(InventoryUsedRoutingResolveService);
    service = TestBed.inject(InventoryUsedService);
    resultInventoryUsed = undefined;
  });

  describe('resolve', () => {
    it('should return IInventoryUsed returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInventoryUsed = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultInventoryUsed).toEqual({ id: 123 });
    });

    it('should return new IInventoryUsed if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInventoryUsed = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultInventoryUsed).toEqual(new InventoryUsed());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as InventoryUsed })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInventoryUsed = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultInventoryUsed).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
