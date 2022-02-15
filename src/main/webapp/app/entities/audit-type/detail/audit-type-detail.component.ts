import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAuditType } from '../audit-type.model';

@Component({
  selector: 'jhi-audit-type-detail',
  templateUrl: './audit-type-detail.component.html',
})
export class AuditTypeDetailComponent implements OnInit {
  auditType: IAuditType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditType }) => {
      this.auditType = auditType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
