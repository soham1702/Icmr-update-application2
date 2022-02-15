import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ICMRDailyCountDetailComponent } from './icmr-daily-count-detail.component';

describe('ICMRDailyCount Management Detail Component', () => {
  let comp: ICMRDailyCountDetailComponent;
  let fixture: ComponentFixture<ICMRDailyCountDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ICMRDailyCountDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ iCMRDailyCount: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ICMRDailyCountDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ICMRDailyCountDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load iCMRDailyCount on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.iCMRDailyCount).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
