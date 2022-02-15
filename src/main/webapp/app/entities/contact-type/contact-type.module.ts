import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ContactTypeComponent } from './list/contact-type.component';
import { ContactTypeDetailComponent } from './detail/contact-type-detail.component';
import { ContactTypeUpdateComponent } from './update/contact-type-update.component';
import { ContactTypeDeleteDialogComponent } from './delete/contact-type-delete-dialog.component';
import { ContactTypeRoutingModule } from './route/contact-type-routing.module';

@NgModule({
  imports: [SharedModule, ContactTypeRoutingModule],
  declarations: [ContactTypeComponent, ContactTypeDetailComponent, ContactTypeUpdateComponent, ContactTypeDeleteDialogComponent],
  entryComponents: [ContactTypeDeleteDialogComponent],
})
export class ContactTypeModule {}
