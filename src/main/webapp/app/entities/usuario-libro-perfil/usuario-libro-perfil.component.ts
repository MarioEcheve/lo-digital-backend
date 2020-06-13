import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUsuarioLibroPerfil } from 'app/shared/model/usuario-libro-perfil.model';
import { UsuarioLibroPerfilService } from './usuario-libro-perfil.service';
import { UsuarioLibroPerfilDeleteDialogComponent } from './usuario-libro-perfil-delete-dialog.component';

@Component({
  selector: 'jhi-usuario-libro-perfil',
  templateUrl: './usuario-libro-perfil.component.html'
})
export class UsuarioLibroPerfilComponent implements OnInit, OnDestroy {
  usuarioLibroPerfils?: IUsuarioLibroPerfil[];
  eventSubscriber?: Subscription;

  constructor(
    protected usuarioLibroPerfilService: UsuarioLibroPerfilService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.usuarioLibroPerfilService
      .query()
      .subscribe((res: HttpResponse<IUsuarioLibroPerfil[]>) => (this.usuarioLibroPerfils = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUsuarioLibroPerfils();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUsuarioLibroPerfil): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUsuarioLibroPerfils(): void {
    this.eventSubscriber = this.eventManager.subscribe('usuarioLibroPerfilListModification', () => this.loadAll());
  }

  delete(usuarioLibroPerfil: IUsuarioLibroPerfil): void {
    const modalRef = this.modalService.open(UsuarioLibroPerfilDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.usuarioLibroPerfil = usuarioLibroPerfil;
  }
}
