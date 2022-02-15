import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventoryUsed } from '../inventory-used.model';

@Component({
  selector: 'jhi-inventory-used-detail',
  templateUrl: './inventory-used-detail.component.html',
})
export class InventoryUsedDetailComponent implements OnInit {
  inventoryUsed: IInventoryUsed | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryUsed }) => {
      this.inventoryUsed = inventoryUsed;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
