import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoFolio } from 'app/shared/model/tipo-folio.model';

@Component({
  selector: 'jhi-tipo-folio-detail',
  templateUrl: './tipo-folio-detail.component.html'
})
export class TipoFolioDetailComponent implements OnInit {
  tipoFolio: ITipoFolio | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoFolio }) => (this.tipoFolio = tipoFolio));
  }

  previousState(): void {
    window.history.back();
  }
}
