import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBedTransactions } from '../bed-transactions.model';

@Component({
  selector: 'jhi-bed-transactions-detail',
  templateUrl: './bed-transactions-detail.component.html',
})
export class BedTransactionsDetailComponent implements OnInit {
  bedTransactions: IBedTransactions | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bedTransactions }) => {
      this.bedTransactions = bedTransactions;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
