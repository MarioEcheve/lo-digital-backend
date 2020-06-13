import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { EntidadComponent } from './entidad.component';
import { EntidadDetailComponent } from './entidad-detail.component';
import { EntidadUpdateComponent } from './entidad-update.component';
import { EntidadDeleteDialogComponent } from './entidad-delete-dialog.component';
import { entidadRoute } from './entidad.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(entidadRoute)],
  declarations: [EntidadComponent, EntidadDetailComponent, EntidadUpdateComponent, EntidadDeleteDialogComponent],
  entryComponents: [EntidadDeleteDialogComponent]
})
export class BackendEntidadModule {}
