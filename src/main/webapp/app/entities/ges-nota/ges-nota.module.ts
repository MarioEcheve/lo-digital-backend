import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { GesNotaComponent } from './ges-nota.component';
import { GesNotaDetailComponent } from './ges-nota-detail.component';
import { GesNotaUpdateComponent } from './ges-nota-update.component';
import { GesNotaDeleteDialogComponent } from './ges-nota-delete-dialog.component';
import { gesNotaRoute } from './ges-nota.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(gesNotaRoute)],
  declarations: [GesNotaComponent, GesNotaDetailComponent, GesNotaUpdateComponent, GesNotaDeleteDialogComponent],
  entryComponents: [GesNotaDeleteDialogComponent]
})
export class BackendGesNotaModule {}
