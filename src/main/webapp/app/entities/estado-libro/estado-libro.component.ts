import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEstadoLibro } from 'app/shared/model/estado-libro.model';
import { EstadoLibroService } from './estado-libro.service';
import { EstadoLibroDeleteDialogComponent } from './estado-libro-delete-dialog.component';

@Component({
  selector: 'jhi-estado-libro',
  templateUrl: './estado-libro.component.html'
})
export class EstadoLibroComponent implements OnInit, OnDestroy {
  estadoLibros?: IEstadoLibro[];
  eventSubscriber?: Subscription;

  constructor(
    protected estadoLibroService: EstadoLibroService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.estadoLibroService.query().subscribe((res: HttpResponse<IEstadoLibro[]>) => (this.estadoLibros = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEstadoLibros();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEstadoLibro): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEstadoLibros(): void {
    this.eventSubscriber = this.eventManager.subscribe('estadoLibroListModification', () => this.loadAll());
  }

  delete(estadoLibro: IEstadoLibro): void {
    const modalRef = this.modalService.open(EstadoLibroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.estadoLibro = estadoLibro;
  }
}
