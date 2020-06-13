import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoMonto } from 'app/shared/model/tipo-monto.model';

@Component({
  selector: 'jhi-tipo-monto-detail',
  templateUrl: './tipo-monto-detail.component.html'
})
export class TipoMontoDetailComponent implements OnInit {
  tipoMonto: ITipoMonto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoMonto }) => (this.tipoMonto = tipoMonto));
  }

  previousState(): void {
    window.history.back();
  }
}
