import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { UsuarioLibroComponent } from './usuario-libro.component';
import { UsuarioLibroDetailComponent } from './usuario-libro-detail.component';
import { UsuarioLibroUpdateComponent } from './usuario-libro-update.component';
import { UsuarioLibroDeleteDialogComponent } from './usuario-libro-delete-dialog.component';
import { usuarioLibroRoute } from './usuario-libro.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(usuarioLibroRoute)],
  declarations: [UsuarioLibroComponent, UsuarioLibroDetailComponent, UsuarioLibroUpdateComponent, UsuarioLibroDeleteDialogComponent],
  entryComponents: [UsuarioLibroDeleteDialogComponent]
})
export class BackendUsuarioLibroModule {}
