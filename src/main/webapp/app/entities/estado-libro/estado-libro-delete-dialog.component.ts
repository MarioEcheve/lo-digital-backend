import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstadoLibro } from 'app/shared/model/estado-libro.model';
import { EstadoLibroService } from './estado-libro.service';

@Component({
  templateUrl: './estado-libro-delete-dialog.component.html'
})
export class EstadoLibroDeleteDialogComponent {
  estadoLibro?: IEstadoLibro;

  constructor(
    protected estadoLibroService: EstadoLibroService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.estadoLibroService.delete(id).subscribe(() => {
      this.eventManager.broadcast('estadoLibroListModification');
      this.activeModal.close();
    });
  }
}
