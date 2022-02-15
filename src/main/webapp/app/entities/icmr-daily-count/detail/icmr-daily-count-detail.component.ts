import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IICMRDailyCount } from '../icmr-daily-count.model';

@Component({
  selector: 'jhi-icmr-daily-count-detail',
  templateUrl: './icmr-daily-count-detail.component.html',
})
export class ICMRDailyCountDetailComponent implements OnInit {
  iCMRDailyCount: IICMRDailyCount | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ iCMRDailyCount }) => {
      this.iCMRDailyCount = iCMRDailyCount;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
