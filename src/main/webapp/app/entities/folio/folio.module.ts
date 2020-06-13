import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { FolioComponent } from './folio.component';
import { FolioDetailComponent } from './folio-detail.component';
import { FolioUpdateComponent } from './folio-update.component';
import { FolioDeleteDialogComponent } from './folio-delete-dialog.component';
import { folioRoute } from './folio.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(folioRoute)],
  declarations: [FolioComponent, FolioDetailComponent, FolioUpdateComponent, FolioDeleteDialogComponent],
  entryComponents: [FolioDeleteDialogComponent]
})
export class BackendFolioModule {}
