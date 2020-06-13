import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoFirma } from 'app/shared/model/tipo-firma.model';
import { TipoFirmaService } from './tipo-firma.service';

@Component({
  templateUrl: './tipo-firma-delete-dialog.component.html'
})
export class TipoFirmaDeleteDialogComponent {
  tipoFirma?: ITipoFirma;

  constructor(protected tipoFirmaService: TipoFirmaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoFirmaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoFirmaListModification');
      this.activeModal.close();
    });
  }
}
