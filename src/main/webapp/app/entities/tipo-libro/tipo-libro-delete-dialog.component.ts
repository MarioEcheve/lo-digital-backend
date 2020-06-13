import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoLibro } from 'app/shared/model/tipo-libro.model';
import { TipoLibroService } from './tipo-libro.service';

@Component({
  templateUrl: './tipo-libro-delete-dialog.component.html'
})
export class TipoLibroDeleteDialogComponent {
  tipoLibro?: ITipoLibro;

  constructor(protected tipoLibroService: TipoLibroService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoLibroService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoLibroListModification');
      this.activeModal.close();
    });
  }
}
