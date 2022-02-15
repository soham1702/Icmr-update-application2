import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BedInventoryDetailComponent } from './bed-inventory-detail.component';

describe('BedInventory Management Detail Component', () => {
  let comp: BedInventoryDetailComponent;
  let fixture: ComponentFixture<BedInventoryDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BedInventoryDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bedInventory: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BedInventoryDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BedInventoryDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bedInventory on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bedInventory).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
