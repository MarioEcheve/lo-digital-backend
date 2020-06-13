import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoUsuarioDependencia } from 'app/shared/model/tipo-usuario-dependencia.model';
import { TipoUsuarioDependenciaService } from './tipo-usuario-dependencia.service';
import { TipoUsuarioDependenciaDeleteDialogComponent } from './tipo-usuario-dependencia-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-usuario-dependencia',
  templateUrl: './tipo-usuario-dependencia.component.html'
})
export class TipoUsuarioDependenciaComponent implements OnInit, OnDestroy {
  tipoUsuarioDependencias?: ITipoUsuarioDependencia[];
  eventSubscriber?: Subscription;

  constructor(
    protected tipoUsuarioDependenciaService: TipoUsuarioDependenciaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tipoUsuarioDependenciaService
      .query()
      .subscribe((res: HttpResponse<ITipoUsuarioDependencia[]>) => (this.tipoUsuarioDependencias = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoUsuarioDependencias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoUsuarioDependencia): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoUsuarioDependencias(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoUsuarioDependenciaListModification', () => this.loadAll());
  }

  delete(tipoUsuarioDependencia: ITipoUsuarioDependencia): void {
    const modalRef = this.modalService.open(TipoUsuarioDependenciaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoUsuarioDependencia = tipoUsuarioDependencia;
  }
}
