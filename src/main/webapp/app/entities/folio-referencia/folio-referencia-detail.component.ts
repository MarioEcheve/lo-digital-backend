import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFolioReferencia } from 'app/shared/model/folio-referencia.model';

@Component({
  selector: 'jhi-folio-referencia-detail',
  templateUrl: './folio-referencia-detail.component.html'
})
export class FolioReferenciaDetailComponent implements OnInit {
  folioReferencia: IFolioReferencia | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ folioReferencia }) => (this.folioReferencia = folioReferencia));
  }

  previousState(): void {
    window.history.back();
  }
}
