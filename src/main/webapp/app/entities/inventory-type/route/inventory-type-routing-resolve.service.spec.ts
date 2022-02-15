import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IInventoryType, InventoryType } from '../inventory-type.model';
import { InventoryTypeService } from '../service/inventory-type.service';

import { InventoryTypeRoutingResolveService } from './inventory-type-routing-resolve.service';

describe('InventoryType routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: InventoryTypeRoutingResolveService;
  let service: InventoryTypeService;
  let resultInventoryType: IInventoryType | undefined;

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
    routingResolveService = TestBed.inject(InventoryTypeRoutingResolveService);
    service = TestBed.inject(InventoryTypeService);
    resultInventoryType = undefined;
  });

  describe('resolve', () => {
    it('should return IInventoryType returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInventoryType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultInventoryType).toEqual({ id: 123 });
    });

    it('should return new IInventoryType if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInventoryType = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultInventoryType).toEqual(new InventoryType());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as InventoryType })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInventoryType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultInventoryType).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
