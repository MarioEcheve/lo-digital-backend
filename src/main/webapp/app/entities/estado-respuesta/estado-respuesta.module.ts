import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { EstadoRespuestaComponent } from './estado-respuesta.component';
import { EstadoRespuestaDetailComponent } from './estado-respuesta-detail.component';
import { EstadoRespuestaUpdateComponent } from './estado-respuesta-update.component';
import { EstadoRespuestaDeleteDialogComponent } from './estado-respuesta-delete-dialog.component';
import { estadoRespuestaRoute } from './estado-respuesta.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(estadoRespuestaRoute)],
  declarations: [
    EstadoRespuestaComponent,
    EstadoRespuestaDetailComponent,
    EstadoRespuestaUpdateComponent,
    EstadoRespuestaDeleteDialogComponent
  ],
  entryComponents: [EstadoRespuestaDeleteDialogComponent]
})
export class BackendEstadoRespuestaModule {}
