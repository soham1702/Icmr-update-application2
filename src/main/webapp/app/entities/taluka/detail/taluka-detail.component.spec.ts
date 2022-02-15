import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TalukaDetailComponent } from './taluka-detail.component';

describe('Taluka Management Detail Component', () => {
  let comp: TalukaDetailComponent;
  let fixture: ComponentFixture<TalukaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TalukaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ taluka: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TalukaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TalukaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load taluka on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.taluka).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
