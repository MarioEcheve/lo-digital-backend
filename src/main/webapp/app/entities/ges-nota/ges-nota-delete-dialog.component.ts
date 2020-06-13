import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGesNota } from 'app/shared/model/ges-nota.model';
import { GesNotaService } from './ges-nota.service';

@Component({
  templateUrl: './ges-nota-delete-dialog.component.html'
})
export class GesNotaDeleteDialogComponent {
  gesNota?: IGesNota;

  constructor(protected gesNotaService: GesNotaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gesNotaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('gesNotaListModification');
      this.activeModal.close();
    });
  }
}
