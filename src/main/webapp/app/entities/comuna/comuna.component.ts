import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IComuna } from 'app/shared/model/comuna.model';
import { ComunaService } from './comuna.service';
import { ComunaDeleteDialogComponent } from './comuna-delete-dialog.component';

@Component({
  selector: 'jhi-comuna',
  templateUrl: './comuna.component.html'
})
export class ComunaComponent implements OnInit, OnDestroy {
  comunas?: IComuna[];
  eventSubscriber?: Subscription;

  constructor(protected comunaService: ComunaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.comunaService.query().subscribe((res: HttpResponse<IComuna[]>) => (this.comunas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInComunas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IComuna): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInComunas(): void {
    this.eventSubscriber = this.eventManager.subscribe('comunaListModification', () => this.loadAll());
  }

  delete(comuna: IComuna): void {
    const modalRef = this.modalService.open(ComunaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.comuna = comuna;
  }
}
