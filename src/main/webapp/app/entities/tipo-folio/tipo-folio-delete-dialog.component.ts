import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoFolio } from 'app/shared/model/tipo-folio.model';
import { TipoFolioService } from './tipo-folio.service';

@Component({
  templateUrl: './tipo-folio-delete-dialog.component.html'
})
export class TipoFolioDeleteDialogComponent {
  tipoFolio?: ITipoFolio;

  constructor(protected tipoFolioService: TipoFolioService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoFolioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoFolioListModification');
      this.activeModal.close();
    });
  }
}
