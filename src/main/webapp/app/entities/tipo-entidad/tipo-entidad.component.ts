import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoEntidad } from 'app/shared/model/tipo-entidad.model';
import { TipoEntidadService } from './tipo-entidad.service';
import { TipoEntidadDeleteDialogComponent } from './tipo-entidad-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-entidad',
  templateUrl: './tipo-entidad.component.html'
})
export class TipoEntidadComponent implements OnInit, OnDestroy {
  tipoEntidads?: ITipoEntidad[];
  eventSubscriber?: Subscription;

  constructor(
    protected tipoEntidadService: TipoEntidadService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tipoEntidadService.query().subscribe((res: HttpResponse<ITipoEntidad[]>) => (this.tipoEntidads = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoEntidads();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoEntidad): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoEntidads(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoEntidadListModification', () => this.loadAll());
  }

  delete(tipoEntidad: ITipoEntidad): void {
    const modalRef = this.modalService.open(TipoEntidadDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoEntidad = tipoEntidad;
  }
}
