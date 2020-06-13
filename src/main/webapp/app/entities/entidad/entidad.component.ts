import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEntidad } from 'app/shared/model/entidad.model';
import { EntidadService } from './entidad.service';
import { EntidadDeleteDialogComponent } from './entidad-delete-dialog.component';

@Component({
  selector: 'jhi-entidad',
  templateUrl: './entidad.component.html'
})
export class EntidadComponent implements OnInit, OnDestroy {
  entidads?: IEntidad[];
  eventSubscriber?: Subscription;

  constructor(protected entidadService: EntidadService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.entidadService.query().subscribe((res: HttpResponse<IEntidad[]>) => (this.entidads = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEntidads();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEntidad): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEntidads(): void {
    this.eventSubscriber = this.eventManager.subscribe('entidadListModification', () => this.loadAll());
  }

  delete(entidad: IEntidad): void {
    const modalRef = this.modalService.open(EntidadDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.entidad = entidad;
  }
}
