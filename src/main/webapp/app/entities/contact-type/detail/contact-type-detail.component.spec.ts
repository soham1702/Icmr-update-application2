import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContactTypeDetailComponent } from './contact-type-detail.component';

describe('ContactType Management Detail Component', () => {
  let comp: ContactTypeDetailComponent;
  let fixture: ComponentFixture<ContactTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ContactTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ contactType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ContactTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ContactTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load contactType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.contactType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
