import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { TipoFirmaComponent } from './tipo-firma.component';
import { TipoFirmaDetailComponent } from './tipo-firma-detail.component';
import { TipoFirmaUpdateComponent } from './tipo-firma-update.component';
import { TipoFirmaDeleteDialogComponent } from './tipo-firma-delete-dialog.component';
import { tipoFirmaRoute } from './tipo-firma.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(tipoFirmaRoute)],
  declarations: [TipoFirmaComponent, TipoFirmaDetailComponent, TipoFirmaUpdateComponent, TipoFirmaDeleteDialogComponent],
  entryComponents: [TipoFirmaDeleteDialogComponent]
})
export class BackendTipoFirmaModule {}
