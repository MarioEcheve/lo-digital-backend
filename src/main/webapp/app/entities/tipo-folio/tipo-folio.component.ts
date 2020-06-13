import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoFolio } from 'app/shared/model/tipo-folio.model';
import { TipoFolioService } from './tipo-folio.service';
import { TipoFolioDeleteDialogComponent } from './tipo-folio-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-folio',
  templateUrl: './tipo-folio.component.html'
})
export class TipoFolioComponent implements OnInit, OnDestroy {
  tipoFolios?: ITipoFolio[];
  eventSubscriber?: Subscription;

  constructor(protected tipoFolioService: TipoFolioService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.tipoFolioService.query().subscribe((res: HttpResponse<ITipoFolio[]>) => (this.tipoFolios = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoFolios();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoFolio): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoFolios(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoFolioListModification', () => this.loadAll());
  }

  delete(tipoFolio: ITipoFolio): void {
    const modalRef = this.modalService.open(TipoFolioDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoFolio = tipoFolio;
  }
}
