import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { TipoContratoComponent } from './tipo-contrato.component';
import { TipoContratoDetailComponent } from './tipo-contrato-detail.component';
import { TipoContratoUpdateComponent } from './tipo-contrato-update.component';
import { TipoContratoDeleteDialogComponent } from './tipo-contrato-delete-dialog.component';
import { tipoContratoRoute } from './tipo-contrato.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(tipoContratoRoute)],
  declarations: [TipoContratoComponent, TipoContratoDetailComponent, TipoContratoUpdateComponent, TipoContratoDeleteDialogComponent],
  entryComponents: [TipoContratoDeleteDialogComponent]
})
export class BackendTipoContratoModule {}
