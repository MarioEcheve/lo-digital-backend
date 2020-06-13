import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoFirma } from 'app/shared/model/tipo-firma.model';
import { TipoFirmaService } from './tipo-firma.service';
import { TipoFirmaDeleteDialogComponent } from './tipo-firma-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-firma',
  templateUrl: './tipo-firma.component.html'
})
export class TipoFirmaComponent implements OnInit, OnDestroy {
  tipoFirmas?: ITipoFirma[];
  eventSubscriber?: Subscription;

  constructor(protected tipoFirmaService: TipoFirmaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.tipoFirmaService.query().subscribe((res: HttpResponse<ITipoFirma[]>) => (this.tipoFirmas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoFirmas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoFirma): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoFirmas(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoFirmaListModification', () => this.loadAll());
  }

  delete(tipoFirma: ITipoFirma): void {
    const modalRef = this.modalService.open(TipoFirmaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoFirma = tipoFirma;
  }
}
