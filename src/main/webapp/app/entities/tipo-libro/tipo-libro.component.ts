import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoLibro } from 'app/shared/model/tipo-libro.model';
import { TipoLibroService } from './tipo-libro.service';
import { TipoLibroDeleteDialogComponent } from './tipo-libro-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-libro',
  templateUrl: './tipo-libro.component.html'
})
export class TipoLibroComponent implements OnInit, OnDestroy {
  tipoLibros?: ITipoLibro[];
  eventSubscriber?: Subscription;

  constructor(protected tipoLibroService: TipoLibroService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.tipoLibroService.query().subscribe((res: HttpResponse<ITipoLibro[]>) => (this.tipoLibros = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoLibros();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoLibro): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoLibros(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoLibroListModification', () => this.loadAll());
  }

  delete(tipoLibro: ITipoLibro): void {
    const modalRef = this.modalService.open(TipoLibroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoLibro = tipoLibro;
  }
}
