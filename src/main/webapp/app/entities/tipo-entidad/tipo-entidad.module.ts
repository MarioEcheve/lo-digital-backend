import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { TipoEntidadComponent } from './tipo-entidad.component';
import { TipoEntidadDetailComponent } from './tipo-entidad-detail.component';
import { TipoEntidadUpdateComponent } from './tipo-entidad-update.component';
import { TipoEntidadDeleteDialogComponent } from './tipo-entidad-delete-dialog.component';
import { tipoEntidadRoute } from './tipo-entidad.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(tipoEntidadRoute)],
  declarations: [TipoEntidadComponent, TipoEntidadDetailComponent, TipoEntidadUpdateComponent, TipoEntidadDeleteDialogComponent],
  entryComponents: [TipoEntidadDeleteDialogComponent]
})
export class BackendTipoEntidadModule {}
