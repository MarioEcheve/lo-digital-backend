import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { GesFavoritoComponent } from './ges-favorito.component';
import { GesFavoritoDetailComponent } from './ges-favorito-detail.component';
import { GesFavoritoUpdateComponent } from './ges-favorito-update.component';
import { GesFavoritoDeleteDialogComponent } from './ges-favorito-delete-dialog.component';
import { gesFavoritoRoute } from './ges-favorito.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(gesFavoritoRoute)],
  declarations: [GesFavoritoComponent, GesFavoritoDetailComponent, GesFavoritoUpdateComponent, GesFavoritoDeleteDialogComponent],
  entryComponents: [GesFavoritoDeleteDialogComponent]
})
export class BackendGesFavoritoModule {}
