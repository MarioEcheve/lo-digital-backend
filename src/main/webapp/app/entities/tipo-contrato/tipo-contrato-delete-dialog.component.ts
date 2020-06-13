import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoContrato } from 'app/shared/model/tipo-contrato.model';
import { TipoContratoService } from './tipo-contrato.service';

@Component({
  templateUrl: './tipo-contrato-delete-dialog.component.html'
})
export class TipoContratoDeleteDialogComponent {
  tipoContrato?: ITipoContrato;

  constructor(
    protected tipoContratoService: TipoContratoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoContratoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoContratoListModification');
      this.activeModal.close();
    });
  }
}
