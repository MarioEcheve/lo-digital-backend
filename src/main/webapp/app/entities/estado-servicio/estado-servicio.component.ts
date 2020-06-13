import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEstadoServicio } from 'app/shared/model/estado-servicio.model';
import { EstadoServicioService } from './estado-servicio.service';
import { EstadoServicioDeleteDialogComponent } from './estado-servicio-delete-dialog.component';

@Component({
  selector: 'jhi-estado-servicio',
  templateUrl: './estado-servicio.component.html'
})
export class EstadoServicioComponent implements OnInit, OnDestroy {
  estadoServicios?: IEstadoServicio[];
  eventSubscriber?: Subscription;

  constructor(
    protected estadoServicioService: EstadoServicioService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.estadoServicioService.query().subscribe((res: HttpResponse<IEstadoServicio[]>) => (this.estadoServicios = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEstadoServicios();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEstadoServicio): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEstadoServicios(): void {
    this.eventSubscriber = this.eventManager.subscribe('estadoServicioListModification', () => this.loadAll());
  }

  delete(estadoServicio: IEstadoServicio): void {
    const modalRef = this.modalService.open(EstadoServicioDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.estadoServicio = estadoServicio;
  }
}
