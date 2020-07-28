import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { FolioReferenciaComponent } from './folio-referencia.component';
import { FolioReferenciaDetailComponent } from './folio-referencia-detail.component';
import { FolioReferenciaUpdateComponent } from './folio-referencia-update.component';
import { FolioReferenciaDeleteDialogComponent } from './folio-referencia-delete-dialog.component';
import { folioReferenciaRoute } from './folio-referencia.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(folioReferenciaRoute)],
  declarations: [
    FolioReferenciaComponent,
    FolioReferenciaDetailComponent,
    FolioReferenciaUpdateComponent,
    FolioReferenciaDeleteDialogComponent
  ],
  entryComponents: [FolioReferenciaDeleteDialogComponent]
})
export class BackendFolioReferenciaModule {}
