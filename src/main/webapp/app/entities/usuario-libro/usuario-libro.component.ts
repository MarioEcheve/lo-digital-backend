import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUsuarioLibro } from 'app/shared/model/usuario-libro.model';
import { UsuarioLibroService } from './usuario-libro.service';
import { UsuarioLibroDeleteDialogComponent } from './usuario-libro-delete-dialog.component';

@Component({
  selector: 'jhi-usuario-libro',
  templateUrl: './usuario-libro.component.html'
})
export class UsuarioLibroComponent implements OnInit, OnDestroy {
  usuarioLibros?: IUsuarioLibro[];
  eventSubscriber?: Subscription;

  constructor(
    protected usuarioLibroService: UsuarioLibroService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.usuarioLibroService.query().subscribe((res: HttpResponse<IUsuarioLibro[]>) => (this.usuarioLibros = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUsuarioLibros();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUsuarioLibro): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUsuarioLibros(): void {
    this.eventSubscriber = this.eventManager.subscribe('usuarioLibroListModification', () => this.loadAll());
  }

  delete(usuarioLibro: IUsuarioLibro): void {
    const modalRef = this.modalService.open(UsuarioLibroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.usuarioLibro = usuarioLibro;
  }
}
