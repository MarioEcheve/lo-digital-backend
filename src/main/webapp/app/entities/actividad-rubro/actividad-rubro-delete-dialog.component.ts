import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActividadRubro } from 'app/shared/model/actividad-rubro.model';
import { ActividadRubroService } from './actividad-rubro.service';

@Component({
  templateUrl: './actividad-rubro-delete-dialog.component.html'
})
export class ActividadRubroDeleteDialogComponent {
  actividadRubro?: IActividadRubro;

  constructor(
    protected actividadRubroService: ActividadRubroService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.actividadRubroService.delete(id).subscribe(() => {
      this.eventManager.broadcast('actividadRubroListModification');
      this.activeModal.close();
    });
  }
}
