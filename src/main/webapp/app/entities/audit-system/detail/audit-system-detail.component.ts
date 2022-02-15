import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAuditSystem } from '../audit-system.model';

@Component({
  selector: 'jhi-audit-system-detail',
  templateUrl: './audit-system-detail.component.html',
})
export class AuditSystemDetailComponent implements OnInit {
  auditSystem: IAuditSystem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditSystem }) => {
      this.auditSystem = auditSystem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
