import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoMonto } from 'app/shared/model/tipo-monto.model';
import { TipoMontoService } from './tipo-monto.service';

@Component({
  templateUrl: './tipo-monto-delete-dialog.component.html'
})
export class TipoMontoDeleteDialogComponent {
  tipoMonto?: ITipoMonto;

  constructor(protected tipoMontoService: TipoMontoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoMontoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoMontoListModification');
      this.activeModal.close();
    });
  }
}
