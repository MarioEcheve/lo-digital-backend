import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { PerfilUsuarioDependenciaComponent } from './perfil-usuario-dependencia.component';
import { PerfilUsuarioDependenciaDetailComponent } from './perfil-usuario-dependencia-detail.component';
import { PerfilUsuarioDependenciaUpdateComponent } from './perfil-usuario-dependencia-update.component';
import { PerfilUsuarioDependenciaDeleteDialogComponent } from './perfil-usuario-dependencia-delete-dialog.component';
import { perfilUsuarioDependenciaRoute } from './perfil-usuario-dependencia.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(perfilUsuarioDependenciaRoute)],
  declarations: [
    PerfilUsuarioDependenciaComponent,
    PerfilUsuarioDependenciaDetailComponent,
    PerfilUsuarioDependenciaUpdateComponent,
    PerfilUsuarioDependenciaDeleteDialogComponent
  ],
  entryComponents: [PerfilUsuarioDependenciaDeleteDialogComponent]
})
export class BackendPerfilUsuarioDependenciaModule {}
