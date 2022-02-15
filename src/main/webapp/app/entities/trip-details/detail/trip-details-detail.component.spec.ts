import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TripDetailsDetailComponent } from './trip-details-detail.component';

describe('TripDetails Management Detail Component', () => {
  let comp: TripDetailsDetailComponent;
  let fixture: ComponentFixture<TripDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TripDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tripDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TripDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TripDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tripDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tripDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
