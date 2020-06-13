import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILibro } from 'app/shared/model/libro.model';
import { LibroService } from './libro.service';

@Component({
  templateUrl: './libro-delete-dialog.component.html'
})
export class LibroDeleteDialogComponent {
  libro?: ILibro;

  constructor(protected libroService: LibroService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.libroService.delete(id).subscribe(() => {
      this.eventManager.broadcast('libroListModification');
      this.activeModal.close();
    });
  }
}
