import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComuna } from 'app/shared/model/comuna.model';
import { ComunaService } from './comuna.service';

@Component({
  templateUrl: './comuna-delete-dialog.component.html'
})
export class ComunaDeleteDialogComponent {
  comuna?: IComuna;

  constructor(protected comunaService: ComunaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.comunaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('comunaListModification');
      this.activeModal.close();
    });
  }
}
