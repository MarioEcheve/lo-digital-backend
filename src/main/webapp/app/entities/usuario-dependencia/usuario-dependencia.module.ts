import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { UsuarioDependenciaComponent } from './usuario-dependencia.component';
import { UsuarioDependenciaDetailComponent } from './usuario-dependencia-detail.component';
import { UsuarioDependenciaUpdateComponent } from './usuario-dependencia-update.component';
import { UsuarioDependenciaDeleteDialogComponent } from './usuario-dependencia-delete-dialog.component';
import { usuarioDependenciaRoute } from './usuario-dependencia.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(usuarioDependenciaRoute)],
  declarations: [
    UsuarioDependenciaComponent,
    UsuarioDependenciaDetailComponent,
    UsuarioDependenciaUpdateComponent,
    UsuarioDependenciaDeleteDialogComponent
  ],
  entryComponents: [UsuarioDependenciaDeleteDialogComponent]
})
export class BackendUsuarioDependenciaModule {}
