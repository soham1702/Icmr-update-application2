import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BedTransactionsDetailComponent } from './bed-transactions-detail.component';

describe('BedTransactions Management Detail Component', () => {
  let comp: BedTransactionsDetailComponent;
  let fixture: ComponentFixture<BedTransactionsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BedTransactionsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bedTransactions: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BedTransactionsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BedTransactionsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bedTransactions on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bedTransactions).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
