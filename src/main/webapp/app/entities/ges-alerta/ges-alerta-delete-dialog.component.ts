import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGesAlerta } from 'app/shared/model/ges-alerta.model';
import { GesAlertaService } from './ges-alerta.service';

@Component({
  templateUrl: './ges-alerta-delete-dialog.component.html'
})
export class GesAlertaDeleteDialogComponent {
  gesAlerta?: IGesAlerta;

  constructor(protected gesAlertaService: GesAlertaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gesAlertaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('gesAlertaListModification');
      this.activeModal.close();
    });
  }
}
