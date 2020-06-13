import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContrato } from 'app/shared/model/contrato.model';
import { ContratoService } from './contrato.service';
import { ContratoDeleteDialogComponent } from './contrato-delete-dialog.component';

@Component({
  selector: 'jhi-contrato',
  templateUrl: './contrato.component.html'
})
export class ContratoComponent implements OnInit, OnDestroy {
  contratoes?: IContrato[];
  eventSubscriber?: Subscription;

  constructor(protected contratoService: ContratoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.contratoService.query().subscribe((res: HttpResponse<IContrato[]>) => (this.contratoes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInContratoes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IContrato): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInContratoes(): void {
    this.eventSubscriber = this.eventManager.subscribe('contratoListModification', () => this.loadAll());
  }

  delete(contrato: IContrato): void {
    const modalRef = this.modalService.open(ContratoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contrato = contrato;
  }
}
