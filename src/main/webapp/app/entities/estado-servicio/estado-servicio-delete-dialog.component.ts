import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstadoServicio } from 'app/shared/model/estado-servicio.model';
import { EstadoServicioService } from './estado-servicio.service';

@Component({
  templateUrl: './estado-servicio-delete-dialog.component.html'
})
export class EstadoServicioDeleteDialogComponent {
  estadoServicio?: IEstadoServicio;

  constructor(
    protected estadoServicioService: EstadoServicioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.estadoServicioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('estadoServicioListModification');
      this.activeModal.close();
    });
  }
}
