import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGesFavorito } from 'app/shared/model/ges-favorito.model';
import { GesFavoritoService } from './ges-favorito.service';
import { GesFavoritoDeleteDialogComponent } from './ges-favorito-delete-dialog.component';

@Component({
  selector: 'jhi-ges-favorito',
  templateUrl: './ges-favorito.component.html'
})
export class GesFavoritoComponent implements OnInit, OnDestroy {
  gesFavoritos?: IGesFavorito[];
  eventSubscriber?: Subscription;

  constructor(
    protected gesFavoritoService: GesFavoritoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.gesFavoritoService.query().subscribe((res: HttpResponse<IGesFavorito[]>) => (this.gesFavoritos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGesFavoritos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGesFavorito): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGesFavoritos(): void {
    this.eventSubscriber = this.eventManager.subscribe('gesFavoritoListModification', () => this.loadAll());
  }

  delete(gesFavorito: IGesFavorito): void {
    const modalRef = this.modalService.open(GesFavoritoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.gesFavorito = gesFavorito;
  }
}
