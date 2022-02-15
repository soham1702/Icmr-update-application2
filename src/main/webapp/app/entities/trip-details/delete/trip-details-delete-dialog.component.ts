import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITripDetails } from '../trip-details.model';
import { TripDetailsService } from '../service/trip-details.service';

@Component({
  templateUrl: './trip-details-delete-dialog.component.html',
})
export class TripDetailsDeleteDialogComponent {
  tripDetails?: ITripDetails;

  constructor(protected tripDetailsService: TripDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tripDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
