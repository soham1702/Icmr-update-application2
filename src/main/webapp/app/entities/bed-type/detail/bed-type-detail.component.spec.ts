import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BedTypeDetailComponent } from './bed-type-detail.component';

describe('BedType Management Detail Component', () => {
  let comp: BedTypeDetailComponent;
  let fixture: ComponentFixture<BedTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BedTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bedType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BedTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BedTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bedType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bedType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
