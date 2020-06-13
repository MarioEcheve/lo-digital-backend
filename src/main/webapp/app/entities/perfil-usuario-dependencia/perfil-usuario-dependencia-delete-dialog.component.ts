import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPerfilUsuarioDependencia } from 'app/shared/model/perfil-usuario-dependencia.model';
import { PerfilUsuarioDependenciaService } from './perfil-usuario-dependencia.service';

@Component({
  templateUrl: './perfil-usuario-dependencia-delete-dialog.component.html'
})
export class PerfilUsuarioDependenciaDeleteDialogComponent {
  perfilUsuarioDependencia?: IPerfilUsuarioDependencia;

  constructor(
    protected perfilUsuarioDependenciaService: PerfilUsuarioDependenciaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.perfilUsuarioDependenciaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('perfilUsuarioDependenciaListModification');
      this.activeModal.close();
    });
  }
}
