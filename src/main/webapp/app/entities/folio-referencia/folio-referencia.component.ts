import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFolioReferencia } from 'app/shared/model/folio-referencia.model';
import { FolioReferenciaService } from './folio-referencia.service';
import { FolioReferenciaDeleteDialogComponent } from './folio-referencia-delete-dialog.component';

@Component({
  selector: 'jhi-folio-referencia',
  templateUrl: './folio-referencia.component.html'
})
export class FolioReferenciaComponent implements OnInit, OnDestroy {
  folioReferencias?: IFolioReferencia[];
  eventSubscriber?: Subscription;

  constructor(
    protected folioReferenciaService: FolioReferenciaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.folioReferenciaService.query().subscribe((res: HttpResponse<IFolioReferencia[]>) => (this.folioReferencias = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFolioReferencias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFolioReferencia): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFolioReferencias(): void {
    this.eventSubscriber = this.eventManager.subscribe('folioReferenciaListModification', () => this.loadAll());
  }

  delete(folioReferencia: IFolioReferencia): void {
    const modalRef = this.modalService.open(FolioReferenciaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.folioReferencia = folioReferencia;
  }
}
