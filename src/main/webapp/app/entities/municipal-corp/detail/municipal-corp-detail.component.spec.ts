import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MunicipalCorpDetailComponent } from './municipal-corp-detail.component';

describe('MunicipalCorp Management Detail Component', () => {
  let comp: MunicipalCorpDetailComponent;
  let fixture: ComponentFixture<MunicipalCorpDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MunicipalCorpDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ municipalCorp: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MunicipalCorpDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MunicipalCorpDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load municipalCorp on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.municipalCorp).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
