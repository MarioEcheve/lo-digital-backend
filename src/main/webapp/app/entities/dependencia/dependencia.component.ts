import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDependencia } from 'app/shared/model/dependencia.model';
import { DependenciaService } from './dependencia.service';
import { DependenciaDeleteDialogComponent } from './dependencia-delete-dialog.component';

@Component({
  selector: 'jhi-dependencia',
  templateUrl: './dependencia.component.html'
})
export class DependenciaComponent implements OnInit, OnDestroy {
  dependencias?: IDependencia[];
  eventSubscriber?: Subscription;

  constructor(
    protected dependenciaService: DependenciaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.dependenciaService.query().subscribe((res: HttpResponse<IDependencia[]>) => (this.dependencias = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDependencias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDependencia): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDependencias(): void {
    this.eventSubscriber = this.eventManager.subscribe('dependenciaListModification', () => this.loadAll());
  }

  delete(dependencia: IDependencia): void {
    const modalRef = this.modalService.open(DependenciaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dependencia = dependencia;
  }
}
