import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { GesAlertaComponent } from './ges-alerta.component';
import { GesAlertaDetailComponent } from './ges-alerta-detail.component';
import { GesAlertaUpdateComponent } from './ges-alerta-update.component';
import { GesAlertaDeleteDialogComponent } from './ges-alerta-delete-dialog.component';
import { gesAlertaRoute } from './ges-alerta.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(gesAlertaRoute)],
  declarations: [GesAlertaComponent, GesAlertaDetailComponent, GesAlertaUpdateComponent, GesAlertaDeleteDialogComponent],
  entryComponents: [GesAlertaDeleteDialogComponent]
})
export class BackendGesAlertaModule {}
