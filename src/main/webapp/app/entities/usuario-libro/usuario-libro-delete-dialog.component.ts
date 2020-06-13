import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUsuarioLibro } from 'app/shared/model/usuario-libro.model';
import { UsuarioLibroService } from './usuario-libro.service';

@Component({
  templateUrl: './usuario-libro-delete-dialog.component.html'
})
export class UsuarioLibroDeleteDialogComponent {
  usuarioLibro?: IUsuarioLibro;

  constructor(
    protected usuarioLibroService: UsuarioLibroService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.usuarioLibroService.delete(id).subscribe(() => {
      this.eventManager.broadcast('usuarioLibroListModification');
      this.activeModal.close();
    });
  }
}
