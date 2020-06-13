import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { TipoMonedaComponent } from './tipo-moneda.component';
import { TipoMonedaDetailComponent } from './tipo-moneda-detail.component';
import { TipoMonedaUpdateComponent } from './tipo-moneda-update.component';
import { TipoMonedaDeleteDialogComponent } from './tipo-moneda-delete-dialog.component';
import { tipoMonedaRoute } from './tipo-moneda.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(tipoMonedaRoute)],
  declarations: [TipoMonedaComponent, TipoMonedaDetailComponent, TipoMonedaUpdateComponent, TipoMonedaDeleteDialogComponent],
  entryComponents: [TipoMonedaDeleteDialogComponent]
})
export class BackendTipoMonedaModule {}
