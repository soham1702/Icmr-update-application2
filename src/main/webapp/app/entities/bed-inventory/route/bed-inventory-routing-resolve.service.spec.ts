import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IBedInventory, BedInventory } from '../bed-inventory.model';
import { BedInventoryService } from '../service/bed-inventory.service';

import { BedInventoryRoutingResolveService } from './bed-inventory-routing-resolve.service';

describe('BedInventory routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: BedInventoryRoutingResolveService;
  let service: BedInventoryService;
  let resultBedInventory: IBedInventory | undefined;

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
    routingResolveService = TestBed.inject(BedInventoryRoutingResolveService);
    service = TestBed.inject(BedInventoryService);
    resultBedInventory = undefined;
  });

  describe('resolve', () => {
    it('should return IBedInventory returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBedInventory = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBedInventory).toEqual({ id: 123 });
    });

    it('should return new IBedInventory if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBedInventory = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultBedInventory).toEqual(new BedInventory());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as BedInventory })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBedInventory = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBedInventory).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
