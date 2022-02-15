import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TransactionsDetailComponent } from './transactions-detail.component';

describe('Transactions Management Detail Component', () => {
  let comp: TransactionsDetailComponent;
  let fixture: ComponentFixture<TransactionsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TransactionsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ transactions: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TransactionsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TransactionsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load transactions on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.transactions).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
