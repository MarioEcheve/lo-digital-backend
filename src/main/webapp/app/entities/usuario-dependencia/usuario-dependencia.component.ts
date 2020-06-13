import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUsuarioDependencia } from 'app/shared/model/usuario-dependencia.model';
import { UsuarioDependenciaService } from './usuario-dependencia.service';
import { UsuarioDependenciaDeleteDialogComponent } from './usuario-dependencia-delete-dialog.component';

@Component({
  selector: 'jhi-usuario-dependencia',
  templateUrl: './usuario-dependencia.component.html'
})
export class UsuarioDependenciaComponent implements OnInit, OnDestroy {
  usuarioDependencias?: IUsuarioDependencia[];
  eventSubscriber?: Subscription;

  constructor(
    protected usuarioDependenciaService: UsuarioDependenciaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.usuarioDependenciaService
      .query()
      .subscribe((res: HttpResponse<IUsuarioDependencia[]>) => (this.usuarioDependencias = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUsuarioDependencias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUsuarioDependencia): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUsuarioDependencias(): void {
    this.eventSubscriber = this.eventManager.subscribe('usuarioDependenciaListModification', () => this.loadAll());
  }

  delete(usuarioDependencia: IUsuarioDependencia): void {
    const modalRef = this.modalService.open(UsuarioDependenciaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.usuarioDependencia = usuarioDependencia;
  }
}
