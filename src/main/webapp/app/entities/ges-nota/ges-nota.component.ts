import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGesNota } from 'app/shared/model/ges-nota.model';
import { GesNotaService } from './ges-nota.service';
import { GesNotaDeleteDialogComponent } from './ges-nota-delete-dialog.component';

@Component({
  selector: 'jhi-ges-nota',
  templateUrl: './ges-nota.component.html'
})
export class GesNotaComponent implements OnInit, OnDestroy {
  gesNotas?: IGesNota[];
  eventSubscriber?: Subscription;

  constructor(protected gesNotaService: GesNotaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.gesNotaService.query().subscribe((res: HttpResponse<IGesNota[]>) => (this.gesNotas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGesNotas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGesNota): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGesNotas(): void {
    this.eventSubscriber = this.eventManager.subscribe('gesNotaListModification', () => this.loadAll());
  }

  delete(gesNota: IGesNota): void {
    const modalRef = this.modalService.open(GesNotaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.gesNota = gesNota;
  }
}
