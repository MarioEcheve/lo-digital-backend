import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDependencia } from 'app/shared/model/dependencia.model';
import { DependenciaService } from './dependencia.service';

@Component({
  templateUrl: './dependencia-delete-dialog.component.html'
})
export class DependenciaDeleteDialogComponent {
  dependencia?: IDependencia;

  constructor(
    protected dependenciaService: DependenciaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dependenciaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dependenciaListModification');
      this.activeModal.close();
    });
  }
}
