import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoMoneda } from 'app/shared/model/tipo-moneda.model';

@Component({
  selector: 'jhi-tipo-moneda-detail',
  templateUrl: './tipo-moneda-detail.component.html'
})
export class TipoMonedaDetailComponent implements OnInit {
  tipoMoneda: ITipoMoneda | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoMoneda }) => (this.tipoMoneda = tipoMoneda));
  }

  previousState(): void {
    window.history.back();
  }
}
