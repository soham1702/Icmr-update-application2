import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TransactionsComponent } from './list/transactions.component';
import { TransactionsDetailComponent } from './detail/transactions-detail.component';
import { TransactionsUpdateComponent } from './update/transactions-update.component';
import { TransactionsDeleteDialogComponent } from './delete/transactions-delete-dialog.component';
import { TransactionsRoutingModule } from './route/transactions-routing.module';

@NgModule({
  imports: [SharedModule, TransactionsRoutingModule],
  declarations: [TransactionsComponent, TransactionsDetailComponent, TransactionsUpdateComponent, TransactionsDeleteDialogComponent],
  entryComponents: [TransactionsDeleteDialogComponent],
})
export class TransactionsModule {}
