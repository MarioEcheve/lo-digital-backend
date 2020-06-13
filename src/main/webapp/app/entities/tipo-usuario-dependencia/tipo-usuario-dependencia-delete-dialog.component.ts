import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoUsuarioDependencia } from 'app/shared/model/tipo-usuario-dependencia.model';
import { TipoUsuarioDependenciaService } from './tipo-usuario-dependencia.service';

@Component({
  templateUrl: './tipo-usuario-dependencia-delete-dialog.component.html'
})
export class TipoUsuarioDependenciaDeleteDialogComponent {
  tipoUsuarioDependencia?: ITipoUsuarioDependencia;

  constructor(
    protected tipoUsuarioDependenciaService: TipoUsuarioDependenciaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoUsuarioDependenciaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoUsuarioDependenciaListModification');
      this.activeModal.close();
    });
  }
}
