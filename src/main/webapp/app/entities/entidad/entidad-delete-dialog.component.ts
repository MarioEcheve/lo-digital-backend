import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEntidad } from 'app/shared/model/entidad.model';
import { EntidadService } from './entidad.service';

@Component({
  templateUrl: './entidad-delete-dialog.component.html'
})
export class EntidadDeleteDialogComponent {
  entidad?: IEntidad;

  constructor(protected entidadService: EntidadService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.entidadService.delete(id).subscribe(() => {
      this.eventManager.broadcast('entidadListModification');
      this.activeModal.close();
    });
  }
}
