import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InventoryMasterDetailComponent } from './inventory-master-detail.component';

describe('InventoryMaster Management Detail Component', () => {
  let comp: InventoryMasterDetailComponent;
  let fixture: ComponentFixture<InventoryMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InventoryMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ inventoryMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(InventoryMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InventoryMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load inventoryMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.inventoryMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
