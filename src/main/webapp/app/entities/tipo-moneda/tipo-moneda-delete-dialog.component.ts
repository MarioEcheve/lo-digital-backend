import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoMoneda } from 'app/shared/model/tipo-moneda.model';
import { TipoMonedaService } from './tipo-moneda.service';

@Component({
  templateUrl: './tipo-moneda-delete-dialog.component.html'
})
export class TipoMonedaDeleteDialogComponent {
  tipoMoneda?: ITipoMoneda;

  constructor(
    protected tipoMonedaService: TipoMonedaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoMonedaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoMonedaListModification');
      this.activeModal.close();
    });
  }
}
