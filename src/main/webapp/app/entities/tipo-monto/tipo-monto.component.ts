import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoMonto } from 'app/shared/model/tipo-monto.model';
import { TipoMontoService } from './tipo-monto.service';
import { TipoMontoDeleteDialogComponent } from './tipo-monto-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-monto',
  templateUrl: './tipo-monto.component.html'
})
export class TipoMontoComponent implements OnInit, OnDestroy {
  tipoMontos?: ITipoMonto[];
  eventSubscriber?: Subscription;

  constructor(protected tipoMontoService: TipoMontoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.tipoMontoService.query().subscribe((res: HttpResponse<ITipoMonto[]>) => (this.tipoMontos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoMontos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoMonto): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoMontos(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoMontoListModification', () => this.loadAll());
  }

  delete(tipoMonto: ITipoMonto): void {
    const modalRef = this.modalService.open(TipoMontoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoMonto = tipoMonto;
  }
}
