import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILibro } from 'app/shared/model/libro.model';
import { LibroService } from './libro.service';
import { LibroDeleteDialogComponent } from './libro-delete-dialog.component';

@Component({
  selector: 'jhi-libro',
  templateUrl: './libro.component.html'
})
export class LibroComponent implements OnInit, OnDestroy {
  libros?: ILibro[];
  eventSubscriber?: Subscription;

  constructor(protected libroService: LibroService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.libroService.query().subscribe((res: HttpResponse<ILibro[]>) => (this.libros = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLibros();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILibro): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLibros(): void {
    this.eventSubscriber = this.eventManager.subscribe('libroListModification', () => this.loadAll());
  }

  delete(libro: ILibro): void {
    const modalRef = this.modalService.open(LibroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.libro = libro;
  }
}
