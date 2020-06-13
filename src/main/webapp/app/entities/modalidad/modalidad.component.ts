import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IModalidad } from 'app/shared/model/modalidad.model';
import { ModalidadService } from './modalidad.service';
import { ModalidadDeleteDialogComponent } from './modalidad-delete-dialog.component';

@Component({
  selector: 'jhi-modalidad',
  templateUrl: './modalidad.component.html'
})
export class ModalidadComponent implements OnInit, OnDestroy {
  modalidads?: IModalidad[];
  eventSubscriber?: Subscription;

  constructor(protected modalidadService: ModalidadService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.modalidadService.query().subscribe((res: HttpResponse<IModalidad[]>) => (this.modalidads = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInModalidads();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IModalidad): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInModalidads(): void {
    this.eventSubscriber = this.eventManager.subscribe('modalidadListModification', () => this.loadAll());
  }

  delete(modalidad: IModalidad): void {
    const modalRef = this.modalService.open(ModalidadDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.modalidad = modalidad;
  }
}
