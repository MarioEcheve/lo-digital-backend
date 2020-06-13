import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoEntidad } from 'app/shared/model/tipo-entidad.model';
import { TipoEntidadService } from './tipo-entidad.service';

@Component({
  templateUrl: './tipo-entidad-delete-dialog.component.html'
})
export class TipoEntidadDeleteDialogComponent {
  tipoEntidad?: ITipoEntidad;

  constructor(
    protected tipoEntidadService: TipoEntidadService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoEntidadService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoEntidadListModification');
      this.activeModal.close();
    });
  }
}
