import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { ActividadRubroComponent } from './actividad-rubro.component';
import { ActividadRubroDetailComponent } from './actividad-rubro-detail.component';
import { ActividadRubroUpdateComponent } from './actividad-rubro-update.component';
import { ActividadRubroDeleteDialogComponent } from './actividad-rubro-delete-dialog.component';
import { actividadRubroRoute } from './actividad-rubro.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(actividadRubroRoute)],
  declarations: [
    ActividadRubroComponent,
    ActividadRubroDetailComponent,
    ActividadRubroUpdateComponent,
    ActividadRubroDeleteDialogComponent
  ],
  entryComponents: [ActividadRubroDeleteDialogComponent]
})
export class BackendActividadRubroModule {}
