import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEstadoRespuesta } from 'app/shared/model/estado-respuesta.model';
import { EstadoRespuestaService } from './estado-respuesta.service';
import { EstadoRespuestaDeleteDialogComponent } from './estado-respuesta-delete-dialog.component';

@Component({
  selector: 'jhi-estado-respuesta',
  templateUrl: './estado-respuesta.component.html'
})
export class EstadoRespuestaComponent implements OnInit, OnDestroy {
  estadoRespuestas?: IEstadoRespuesta[];
  eventSubscriber?: Subscription;

  constructor(
    protected estadoRespuestaService: EstadoRespuestaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.estadoRespuestaService.query().subscribe((res: HttpResponse<IEstadoRespuesta[]>) => (this.estadoRespuestas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEstadoRespuestas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEstadoRespuesta): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEstadoRespuestas(): void {
    this.eventSubscriber = this.eventManager.subscribe('estadoRespuestaListModification', () => this.loadAll());
  }

  delete(estadoRespuesta: IEstadoRespuesta): void {
    const modalRef = this.modalService.open(EstadoRespuestaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.estadoRespuesta = estadoRespuesta;
  }
}
