import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { TipoMontoComponent } from './tipo-monto.component';
import { TipoMontoDetailComponent } from './tipo-monto-detail.component';
import { TipoMontoUpdateComponent } from './tipo-monto-update.component';
import { TipoMontoDeleteDialogComponent } from './tipo-monto-delete-dialog.component';
import { tipoMontoRoute } from './tipo-monto.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(tipoMontoRoute)],
  declarations: [TipoMontoComponent, TipoMontoDetailComponent, TipoMontoUpdateComponent, TipoMontoDeleteDialogComponent],
  entryComponents: [TipoMontoDeleteDialogComponent]
})
export class BackendTipoMontoModule {}
