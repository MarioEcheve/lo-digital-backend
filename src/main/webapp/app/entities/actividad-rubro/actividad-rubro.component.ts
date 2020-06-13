import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IActividadRubro } from 'app/shared/model/actividad-rubro.model';
import { ActividadRubroService } from './actividad-rubro.service';
import { ActividadRubroDeleteDialogComponent } from './actividad-rubro-delete-dialog.component';

@Component({
  selector: 'jhi-actividad-rubro',
  templateUrl: './actividad-rubro.component.html'
})
export class ActividadRubroComponent implements OnInit, OnDestroy {
  actividadRubros?: IActividadRubro[];
  eventSubscriber?: Subscription;

  constructor(
    protected actividadRubroService: ActividadRubroService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.actividadRubroService.query().subscribe((res: HttpResponse<IActividadRubro[]>) => (this.actividadRubros = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInActividadRubros();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IActividadRubro): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInActividadRubros(): void {
    this.eventSubscriber = this.eventManager.subscribe('actividadRubroListModification', () => this.loadAll());
  }

  delete(actividadRubro: IActividadRubro): void {
    const modalRef = this.modalService.open(ActividadRubroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.actividadRubro = actividadRubro;
  }
}
