import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IBedTransactions, BedTransactions } from '../bed-transactions.model';
import { BedTransactionsService } from '../service/bed-transactions.service';

import { BedTransactionsRoutingResolveService } from './bed-transactions-routing-resolve.service';

describe('BedTransactions routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: BedTransactionsRoutingResolveService;
  let service: BedTransactionsService;
  let resultBedTransactions: IBedTransactions | undefined;

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
    routingResolveService = TestBed.inject(BedTransactionsRoutingResolveService);
    service = TestBed.inject(BedTransactionsService);
    resultBedTransactions = undefined;
  });

  describe('resolve', () => {
    it('should return IBedTransactions returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBedTransactions = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBedTransactions).toEqual({ id: 123 });
    });

    it('should return new IBedTransactions if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBedTransactions = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultBedTransactions).toEqual(new BedTransactions());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as BedTransactions })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBedTransactions = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBedTransactions).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
