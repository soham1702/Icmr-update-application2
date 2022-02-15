import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AuditTypeDetailComponent } from './audit-type-detail.component';

describe('AuditType Management Detail Component', () => {
  let comp: AuditTypeDetailComponent;
  let fixture: ComponentFixture<AuditTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AuditTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ auditType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AuditTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AuditTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load auditType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.auditType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
