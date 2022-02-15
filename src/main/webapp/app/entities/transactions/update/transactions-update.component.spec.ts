import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TransactionsService } from '../service/transactions.service';
import { ITransactions, Transactions } from '../transactions.model';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { SupplierService } from 'app/entities/supplier/service/supplier.service';
import { IInventory } from 'app/entities/inventory/inventory.model';
import { InventoryService } from 'app/entities/inventory/service/inventory.service';

import { TransactionsUpdateComponent } from './transactions-update.component';

describe('Transactions Management Update Component', () => {
  let comp: TransactionsUpdateComponent;
  let fixture: ComponentFixture<TransactionsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let transactionsService: TransactionsService;
  let supplierService: SupplierService;
  let inventoryService: InventoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TransactionsUpdateComponent],
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
      .overrideTemplate(TransactionsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransactionsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    transactionsService = TestBed.inject(TransactionsService);
    supplierService = TestBed.inject(SupplierService);
    inventoryService = TestBed.inject(InventoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Supplier query and add missing value', () => {
      const transactions: ITransactions = { id: 456 };
      const supplier: ISupplier = { id: 83470 };
      transactions.supplier = supplier;

      const supplierCollection: ISupplier[] = [{ id: 63951 }];
      jest.spyOn(supplierService, 'query').mockReturnValue(of(new HttpResponse({ body: supplierCollection })));
      const additionalSuppliers = [supplier];
      const expectedCollection: ISupplier[] = [...additionalSuppliers, ...supplierCollection];
      jest.spyOn(supplierService, 'addSupplierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      expect(supplierService.query).toHaveBeenCalled();
      expect(supplierService.addSupplierToCollectionIfMissing).toHaveBeenCalledWith(supplierCollection, ...additionalSuppliers);
      expect(comp.suppliersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Inventory query and add missing value', () => {
      const transactions: ITransactions = { id: 456 };
      const inventory: IInventory = { id: 81625 };
      transactions.inventory = inventory;

      const inventoryCollection: IInventory[] = [{ id: 61861 }];
      jest.spyOn(inventoryService, 'query').mockReturnValue(of(new HttpResponse({ body: inventoryCollection })));
      const additionalInventories = [inventory];
      const expectedCollection: IInventory[] = [...additionalInventories, ...inventoryCollection];
      jest.spyOn(inventoryService, 'addInventoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      expect(inventoryService.query).toHaveBeenCalled();
      expect(inventoryService.addInventoryToCollectionIfMissing).toHaveBeenCalledWith(inventoryCollection, ...additionalInventories);
      expect(comp.inventoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const transactions: ITransactions = { id: 456 };
      const supplier: ISupplier = { id: 22177 };
      transactions.supplier = supplier;
      const inventory: IInventory = { id: 2611 };
      transactions.inventory = inventory;

      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(transactions));
      expect(comp.suppliersSharedCollection).toContain(supplier);
      expect(comp.inventoriesSharedCollection).toContain(inventory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Transactions>>();
      const transactions = { id: 123 };
      jest.spyOn(transactionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transactions }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(transactionsService.update).toHaveBeenCalledWith(transactions);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Transactions>>();
      const transactions = new Transactions();
      jest.spyOn(transactionsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transactions }));
      saveSubject.complete();

      // THEN
      expect(transactionsService.create).toHaveBeenCalledWith(transactions);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Transactions>>();
      const transactions = { id: 123 };
      jest.spyOn(transactionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(transactionsService.update).toHaveBeenCalledWith(transactions);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSupplierById', () => {
      it('Should return tracked Supplier primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSupplierById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackInventoryById', () => {
      it('Should return tracked Inventory primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackInventoryById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
