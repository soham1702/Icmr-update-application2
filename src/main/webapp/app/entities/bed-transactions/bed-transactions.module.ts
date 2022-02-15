import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BedTransactionsComponent } from './list/bed-transactions.component';
import { BedTransactionsDetailComponent } from './detail/bed-transactions-detail.component';
import { BedTransactionsUpdateComponent } from './update/bed-transactions-update.component';
import { BedTransactionsDeleteDialogComponent } from './delete/bed-transactions-delete-dialog.component';
import { BedTransactionsRoutingModule } from './route/bed-transactions-routing.module';

@NgModule({
  imports: [SharedModule, BedTransactionsRoutingModule],
  declarations: [
    BedTransactionsComponent,
    BedTransactionsDetailComponent,
    BedTransactionsUpdateComponent,
    BedTransactionsDeleteDialogComponent,
  ],
  entryComponents: [BedTransactionsDeleteDialogComponent],
})
export class BedTransactionsModule {}
