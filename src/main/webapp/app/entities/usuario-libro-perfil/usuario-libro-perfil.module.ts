import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { UsuarioLibroPerfilComponent } from './usuario-libro-perfil.component';
import { UsuarioLibroPerfilDetailComponent } from './usuario-libro-perfil-detail.component';
import { UsuarioLibroPerfilUpdateComponent } from './usuario-libro-perfil-update.component';
import { UsuarioLibroPerfilDeleteDialogComponent } from './usuario-libro-perfil-delete-dialog.component';
import { usuarioLibroPerfilRoute } from './usuario-libro-perfil.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(usuarioLibroPerfilRoute)],
  declarations: [
    UsuarioLibroPerfilComponent,
    UsuarioLibroPerfilDetailComponent,
    UsuarioLibroPerfilUpdateComponent,
    UsuarioLibroPerfilDeleteDialogComponent
  ],
  entryComponents: [UsuarioLibroPerfilDeleteDialogComponent]
})
export class BackendUsuarioLibroPerfilModule {}
