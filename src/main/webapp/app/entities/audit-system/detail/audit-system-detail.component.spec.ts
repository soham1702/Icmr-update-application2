import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AuditSystemDetailComponent } from './audit-system-detail.component';

describe('AuditSystem Management Detail Component', () => {
  let comp: AuditSystemDetailComponent;
  let fixture: ComponentFixture<AuditSystemDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AuditSystemDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ auditSystem: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AuditSystemDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AuditSystemDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load auditSystem on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.auditSystem).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
