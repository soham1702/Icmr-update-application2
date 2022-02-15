import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventoryType } from '../inventory-type.model';

@Component({
  selector: 'jhi-inventory-type-detail',
  templateUrl: './inventory-type-detail.component.html',
})
export class InventoryTypeDetailComponent implements OnInit {
  inventoryType: IInventoryType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryType }) => {
      this.inventoryType = inventoryType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
