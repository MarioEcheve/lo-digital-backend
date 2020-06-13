import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IModalidad } from 'app/shared/model/modalidad.model';
import { ModalidadService } from './modalidad.service';

@Component({
  templateUrl: './modalidad-delete-dialog.component.html'
})
export class ModalidadDeleteDialogComponent {
  modalidad?: IModalidad;

  constructor(protected modalidadService: ModalidadService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.modalidadService.delete(id).subscribe(() => {
      this.eventManager.broadcast('modalidadListModification');
      this.activeModal.close();
    });
  }
}
