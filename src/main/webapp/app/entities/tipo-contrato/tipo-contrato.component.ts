import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoContrato } from 'app/shared/model/tipo-contrato.model';
import { TipoContratoService } from './tipo-contrato.service';
import { TipoContratoDeleteDialogComponent } from './tipo-contrato-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-contrato',
  templateUrl: './tipo-contrato.component.html'
})
export class TipoContratoComponent implements OnInit, OnDestroy {
  tipoContratoes?: ITipoContrato[];
  eventSubscriber?: Subscription;

  constructor(
    protected tipoContratoService: TipoContratoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tipoContratoService.query().subscribe((res: HttpResponse<ITipoContrato[]>) => (this.tipoContratoes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoContratoes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoContrato): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoContratoes(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoContratoListModification', () => this.loadAll());
  }

  delete(tipoContrato: ITipoContrato): void {
    const modalRef = this.modalService.open(TipoContratoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoContrato = tipoContrato;
  }
}
