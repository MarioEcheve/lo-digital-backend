import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { EstadoLibroComponent } from './estado-libro.component';
import { EstadoLibroDetailComponent } from './estado-libro-detail.component';
import { EstadoLibroUpdateComponent } from './estado-libro-update.component';
import { EstadoLibroDeleteDialogComponent } from './estado-libro-delete-dialog.component';
import { estadoLibroRoute } from './estado-libro.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(estadoLibroRoute)],
  declarations: [EstadoLibroComponent, EstadoLibroDetailComponent, EstadoLibroUpdateComponent, EstadoLibroDeleteDialogComponent],
  entryComponents: [EstadoLibroDeleteDialogComponent]
})
export class BackendEstadoLibroModule {}
