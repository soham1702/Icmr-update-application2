import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PatientInfoDetailComponent } from './patient-info-detail.component';

describe('PatientInfo Management Detail Component', () => {
  let comp: PatientInfoDetailComponent;
  let fixture: ComponentFixture<PatientInfoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PatientInfoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ patientInfo: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PatientInfoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PatientInfoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load patientInfo on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.patientInfo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
