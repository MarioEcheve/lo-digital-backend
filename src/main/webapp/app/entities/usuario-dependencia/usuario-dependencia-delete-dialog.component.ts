import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUsuarioDependencia } from 'app/shared/model/usuario-dependencia.model';
import { UsuarioDependenciaService } from './usuario-dependencia.service';

@Component({
  templateUrl: './usuario-dependencia-delete-dialog.component.html'
})
export class UsuarioDependenciaDeleteDialogComponent {
  usuarioDependencia?: IUsuarioDependencia;

  constructor(
    protected usuarioDependenciaService: UsuarioDependenciaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.usuarioDependenciaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('usuarioDependenciaListModification');
      this.activeModal.close();
    });
  }
}
