import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGesAlerta } from 'app/shared/model/ges-alerta.model';
import { GesAlertaService } from './ges-alerta.service';
import { GesAlertaDeleteDialogComponent } from './ges-alerta-delete-dialog.component';

@Component({
  selector: 'jhi-ges-alerta',
  templateUrl: './ges-alerta.component.html'
})
export class GesAlertaComponent implements OnInit, OnDestroy {
  gesAlertas?: IGesAlerta[];
  eventSubscriber?: Subscription;

  constructor(protected gesAlertaService: GesAlertaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.gesAlertaService.query().subscribe((res: HttpResponse<IGesAlerta[]>) => (this.gesAlertas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGesAlertas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGesAlerta): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGesAlertas(): void {
    this.eventSubscriber = this.eventManager.subscribe('gesAlertaListModification', () => this.loadAll());
  }

  delete(gesAlerta: IGesAlerta): void {
    const modalRef = this.modalService.open(GesAlertaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.gesAlerta = gesAlerta;
  }
}
