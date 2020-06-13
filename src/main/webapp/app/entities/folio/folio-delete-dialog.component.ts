import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFolio } from 'app/shared/model/folio.model';
import { FolioService } from './folio.service';

@Component({
  templateUrl: './folio-delete-dialog.component.html'
})
export class FolioDeleteDialogComponent {
  folio?: IFolio;

  constructor(protected folioService: FolioService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.folioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('folioListModification');
      this.activeModal.close();
    });
  }
}
