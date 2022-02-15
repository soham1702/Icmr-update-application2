import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MunicipalCorpComponent } from './list/municipal-corp.component';
import { MunicipalCorpDetailComponent } from './detail/municipal-corp-detail.component';
import { MunicipalCorpUpdateComponent } from './update/municipal-corp-update.component';
import { MunicipalCorpDeleteDialogComponent } from './delete/municipal-corp-delete-dialog.component';
import { MunicipalCorpRoutingModule } from './route/municipal-corp-routing.module';

@NgModule({
  imports: [SharedModule, MunicipalCorpRoutingModule],
  declarations: [MunicipalCorpComponent, MunicipalCorpDetailComponent, MunicipalCorpUpdateComponent, MunicipalCorpDeleteDialogComponent],
  entryComponents: [MunicipalCorpDeleteDialogComponent],
})
export class MunicipalCorpModule {}
