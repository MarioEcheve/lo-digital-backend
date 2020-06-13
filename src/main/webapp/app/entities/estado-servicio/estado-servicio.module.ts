import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { EstadoServicioComponent } from './estado-servicio.component';
import { EstadoServicioDetailComponent } from './estado-servicio-detail.component';
import { EstadoServicioUpdateComponent } from './estado-servicio-update.component';
import { EstadoServicioDeleteDialogComponent } from './estado-servicio-delete-dialog.component';
import { estadoServicioRoute } from './estado-servicio.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(estadoServicioRoute)],
  declarations: [
    EstadoServicioComponent,
    EstadoServicioDetailComponent,
    EstadoServicioUpdateComponent,
    EstadoServicioDeleteDialogComponent
  ],
  entryComponents: [EstadoServicioDeleteDialogComponent]
})
export class BackendEstadoServicioModule {}
