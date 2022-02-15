import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IInventoryMaster, InventoryMaster } from '../inventory-master.model';
import { InventoryMasterService } from '../service/inventory-master.service';

import { InventoryMasterRoutingResolveService } from './inventory-master-routing-resolve.service';

describe('InventoryMaster routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: InventoryMasterRoutingResolveService;
  let service: InventoryMasterService;
  let resultInventoryMaster: IInventoryMaster | undefined;

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
    routingResolveService = TestBed.inject(InventoryMasterRoutingResolveService);
    service = TestBed.inject(InventoryMasterService);
    resultInventoryMaster = undefined;
  });

  describe('resolve', () => {
    it('should return IInventoryMaster returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInventoryMaster = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultInventoryMaster).toEqual({ id: 123 });
    });

    it('should return new IInventoryMaster if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInventoryMaster = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultInventoryMaster).toEqual(new InventoryMaster());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as InventoryMaster })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInventoryMaster = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultInventoryMaster).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
