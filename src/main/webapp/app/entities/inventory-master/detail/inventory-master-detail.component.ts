import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventoryMaster } from '../inventory-master.model';

@Component({
  selector: 'jhi-inventory-master-detail',
  templateUrl: './inventory-master-detail.component.html',
})
export class InventoryMasterDetailComponent implements OnInit {
  inventoryMaster: IInventoryMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryMaster }) => {
      this.inventoryMaster = inventoryMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
