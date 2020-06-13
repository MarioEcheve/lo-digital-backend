import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { LibroComponent } from './libro.component';
import { LibroDetailComponent } from './libro-detail.component';
import { LibroUpdateComponent } from './libro-update.component';
import { LibroDeleteDialogComponent } from './libro-delete-dialog.component';
import { libroRoute } from './libro.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(libroRoute)],
  declarations: [LibroComponent, LibroDetailComponent, LibroUpdateComponent, LibroDeleteDialogComponent],
  entryComponents: [LibroDeleteDialogComponent]
})
export class BackendLibroModule {}
