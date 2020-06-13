import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { ComunaComponent } from './comuna.component';
import { ComunaDetailComponent } from './comuna-detail.component';
import { ComunaUpdateComponent } from './comuna-update.component';
import { ComunaDeleteDialogComponent } from './comuna-delete-dialog.component';
import { comunaRoute } from './comuna.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(comunaRoute)],
  declarations: [ComunaComponent, ComunaDetailComponent, ComunaUpdateComponent, ComunaDeleteDialogComponent],
  entryComponents: [ComunaDeleteDialogComponent]
})
export class BackendComunaModule {}
