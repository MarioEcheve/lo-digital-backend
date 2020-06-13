import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { ArchivoComponent } from './archivo.component';
import { ArchivoDetailComponent } from './archivo-detail.component';
import { ArchivoUpdateComponent } from './archivo-update.component';
import { ArchivoDeleteDialogComponent } from './archivo-delete-dialog.component';
import { archivoRoute } from './archivo.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(archivoRoute)],
  declarations: [ArchivoComponent, ArchivoDetailComponent, ArchivoUpdateComponent, ArchivoDeleteDialogComponent],
  entryComponents: [ArchivoDeleteDialogComponent]
})
export class BackendArchivoModule {}
