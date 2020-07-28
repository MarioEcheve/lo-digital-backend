import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFolioReferencia } from 'app/shared/model/folio-referencia.model';
import { FolioReferenciaService } from './folio-referencia.service';

@Component({
  templateUrl: './folio-referencia-delete-dialog.component.html'
})
export class FolioReferenciaDeleteDialogComponent {
  folioReferencia?: IFolioReferencia;

  constructor(
    protected folioReferenciaService: FolioReferenciaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.folioReferenciaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('folioReferenciaListModification');
      this.activeModal.close();
    });
  }
}
