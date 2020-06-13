import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUsuarioLibroPerfil } from 'app/shared/model/usuario-libro-perfil.model';
import { UsuarioLibroPerfilService } from './usuario-libro-perfil.service';

@Component({
  templateUrl: './usuario-libro-perfil-delete-dialog.component.html'
})
export class UsuarioLibroPerfilDeleteDialogComponent {
  usuarioLibroPerfil?: IUsuarioLibroPerfil;

  constructor(
    protected usuarioLibroPerfilService: UsuarioLibroPerfilService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.usuarioLibroPerfilService.delete(id).subscribe(() => {
      this.eventManager.broadcast('usuarioLibroPerfilListModification');
      this.activeModal.close();
    });
  }
}
