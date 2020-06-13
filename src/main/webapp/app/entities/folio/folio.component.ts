import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFolio } from 'app/shared/model/folio.model';
import { FolioService } from './folio.service';
import { FolioDeleteDialogComponent } from './folio-delete-dialog.component';

@Component({
  selector: 'jhi-folio',
  templateUrl: './folio.component.html'
})
export class FolioComponent implements OnInit, OnDestroy {
  folios?: IFolio[];
  eventSubscriber?: Subscription;

  constructor(
    protected folioService: FolioService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.folioService.query().subscribe((res: HttpResponse<IFolio[]>) => (this.folios = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFolios();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFolio): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInFolios(): void {
    this.eventSubscriber = this.eventManager.subscribe('folioListModification', () => this.loadAll());
  }

  delete(folio: IFolio): void {
    const modalRef = this.modalService.open(FolioDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.folio = folio;
  }
}
