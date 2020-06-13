import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContrato } from 'app/shared/model/contrato.model';
import { ContratoService } from './contrato.service';

@Component({
  templateUrl: './contrato-delete-dialog.component.html'
})
export class ContratoDeleteDialogComponent {
  contrato?: IContrato;

  constructor(protected contratoService: ContratoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contratoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contratoListModification');
      this.activeModal.close();
    });
  }
}
