import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArchivo } from 'app/shared/model/archivo.model';
import { ArchivoService } from './archivo.service';

@Component({
  templateUrl: './archivo-delete-dialog.component.html'
})
export class ArchivoDeleteDialogComponent {
  archivo?: IArchivo;

  constructor(protected archivoService: ArchivoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.archivoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('archivoListModification');
      this.activeModal.close();
    });
  }
}
