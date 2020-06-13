import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstadoRespuesta } from 'app/shared/model/estado-respuesta.model';
import { EstadoRespuestaService } from './estado-respuesta.service';

@Component({
  templateUrl: './estado-respuesta-delete-dialog.component.html'
})
export class EstadoRespuestaDeleteDialogComponent {
  estadoRespuesta?: IEstadoRespuesta;

  constructor(
    protected estadoRespuestaService: EstadoRespuestaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.estadoRespuestaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('estadoRespuestaListModification');
      this.activeModal.close();
    });
  }
}
