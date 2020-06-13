import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { TipoFolioComponent } from './tipo-folio.component';
import { TipoFolioDetailComponent } from './tipo-folio-detail.component';
import { TipoFolioUpdateComponent } from './tipo-folio-update.component';
import { TipoFolioDeleteDialogComponent } from './tipo-folio-delete-dialog.component';
import { tipoFolioRoute } from './tipo-folio.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(tipoFolioRoute)],
  declarations: [TipoFolioComponent, TipoFolioDetailComponent, TipoFolioUpdateComponent, TipoFolioDeleteDialogComponent],
  entryComponents: [TipoFolioDeleteDialogComponent]
})
export class BackendTipoFolioModule {}
