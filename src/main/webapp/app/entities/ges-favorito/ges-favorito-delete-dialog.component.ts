import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGesFavorito } from 'app/shared/model/ges-favorito.model';
import { GesFavoritoService } from './ges-favorito.service';

@Component({
  templateUrl: './ges-favorito-delete-dialog.component.html'
})
export class GesFavoritoDeleteDialogComponent {
  gesFavorito?: IGesFavorito;

  constructor(
    protected gesFavoritoService: GesFavoritoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gesFavoritoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('gesFavoritoListModification');
      this.activeModal.close();
    });
  }
}
