import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPerfilUsuarioDependencia } from 'app/shared/model/perfil-usuario-dependencia.model';
import { PerfilUsuarioDependenciaService } from './perfil-usuario-dependencia.service';
import { PerfilUsuarioDependenciaDeleteDialogComponent } from './perfil-usuario-dependencia-delete-dialog.component';

@Component({
  selector: 'jhi-perfil-usuario-dependencia',
  templateUrl: './perfil-usuario-dependencia.component.html'
})
export class PerfilUsuarioDependenciaComponent implements OnInit, OnDestroy {
  perfilUsuarioDependencias?: IPerfilUsuarioDependencia[];
  eventSubscriber?: Subscription;

  constructor(
    protected perfilUsuarioDependenciaService: PerfilUsuarioDependenciaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.perfilUsuarioDependenciaService
      .query()
      .subscribe((res: HttpResponse<IPerfilUsuarioDependencia[]>) => (this.perfilUsuarioDependencias = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPerfilUsuarioDependencias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPerfilUsuarioDependencia): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPerfilUsuarioDependencias(): void {
    this.eventSubscriber = this.eventManager.subscribe('perfilUsuarioDependenciaListModification', () => this.loadAll());
  }

  delete(perfilUsuarioDependencia: IPerfilUsuarioDependencia): void {
    const modalRef = this.modalService.open(PerfilUsuarioDependenciaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.perfilUsuarioDependencia = perfilUsuarioDependencia;
  }
}
