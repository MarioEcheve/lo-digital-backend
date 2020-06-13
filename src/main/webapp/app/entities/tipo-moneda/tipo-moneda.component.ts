import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoMoneda } from 'app/shared/model/tipo-moneda.model';
import { TipoMonedaService } from './tipo-moneda.service';
import { TipoMonedaDeleteDialogComponent } from './tipo-moneda-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-moneda',
  templateUrl: './tipo-moneda.component.html'
})
export class TipoMonedaComponent implements OnInit, OnDestroy {
  tipoMonedas?: ITipoMoneda[];
  eventSubscriber?: Subscription;

  constructor(protected tipoMonedaService: TipoMonedaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.tipoMonedaService.query().subscribe((res: HttpResponse<ITipoMoneda[]>) => (this.tipoMonedas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoMonedas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoMoneda): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoMonedas(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoMonedaListModification', () => this.loadAll());
  }

  delete(tipoMoneda: ITipoMoneda): void {
    const modalRef = this.modalService.open(TipoMonedaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoMoneda = tipoMoneda;
  }
}
