import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HospitalTypeDetailComponent } from './hospital-type-detail.component';

describe('HospitalType Management Detail Component', () => {
  let comp: HospitalTypeDetailComponent;
  let fixture: ComponentFixture<HospitalTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HospitalTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ hospitalType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(HospitalTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(HospitalTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load hospitalType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.hospitalType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
