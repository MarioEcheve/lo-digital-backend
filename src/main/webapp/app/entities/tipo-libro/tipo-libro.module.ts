import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { TipoLibroComponent } from './tipo-libro.component';
import { TipoLibroDetailComponent } from './tipo-libro-detail.component';
import { TipoLibroUpdateComponent } from './tipo-libro-update.component';
import { TipoLibroDeleteDialogComponent } from './tipo-libro-delete-dialog.component';
import { tipoLibroRoute } from './tipo-libro.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(tipoLibroRoute)],
  declarations: [TipoLibroComponent, TipoLibroDetailComponent, TipoLibroUpdateComponent, TipoLibroDeleteDialogComponent],
  entryComponents: [TipoLibroDeleteDialogComponent]
})
export class BackendTipoLibroModule {}
