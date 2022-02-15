import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InventoryUsedDetailComponent } from './inventory-used-detail.component';

describe('InventoryUsed Management Detail Component', () => {
  let comp: InventoryUsedDetailComponent;
  let fixture: ComponentFixture<InventoryUsedDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InventoryUsedDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ inventoryUsed: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(InventoryUsedDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InventoryUsedDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load inventoryUsed on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.inventoryUsed).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
