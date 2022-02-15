import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InventoryTypeDetailComponent } from './inventory-type-detail.component';

describe('InventoryType Management Detail Component', () => {
  let comp: InventoryTypeDetailComponent;
  let fixture: ComponentFixture<InventoryTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InventoryTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ inventoryType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(InventoryTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InventoryTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load inventoryType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.inventoryType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
