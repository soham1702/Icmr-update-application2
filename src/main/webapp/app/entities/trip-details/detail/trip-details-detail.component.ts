import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITripDetails } from '../trip-details.model';

@Component({
  selector: 'jhi-trip-details-detail',
  templateUrl: './trip-details-detail.component.html',
})
export class TripDetailsDetailComponent implements OnInit {
  tripDetails: ITripDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tripDetails }) => {
      this.tripDetails = tripDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
