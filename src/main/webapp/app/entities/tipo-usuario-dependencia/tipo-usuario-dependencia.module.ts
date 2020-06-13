import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { TipoUsuarioDependenciaComponent } from './tipo-usuario-dependencia.component';
import { TipoUsuarioDependenciaDetailComponent } from './tipo-usuario-dependencia-detail.component';
import { TipoUsuarioDependenciaUpdateComponent } from './tipo-usuario-dependencia-update.component';
import { TipoUsuarioDependenciaDeleteDialogComponent } from './tipo-usuario-dependencia-delete-dialog.component';
import { tipoUsuarioDependenciaRoute } from './tipo-usuario-dependencia.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(tipoUsuarioDependenciaRoute)],
  declarations: [
    TipoUsuarioDependenciaComponent,
    TipoUsuarioDependenciaDetailComponent,
    TipoUsuarioDependenciaUpdateComponent,
    TipoUsuarioDependenciaDeleteDialogComponent
  ],
  entryComponents: [TipoUsuarioDependenciaDeleteDialogComponent]
})
export class BackendTipoUsuarioDependenciaModule {}
