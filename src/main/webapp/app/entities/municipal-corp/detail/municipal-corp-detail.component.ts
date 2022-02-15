import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMunicipalCorp } from '../municipal-corp.model';

@Component({
  selector: 'jhi-municipal-corp-detail',
  templateUrl: './municipal-corp-detail.component.html',
})
export class MunicipalCorpDetailComponent implements OnInit {
  municipalCorp: IMunicipalCorp | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ municipalCorp }) => {
      this.municipalCorp = municipalCorp;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
