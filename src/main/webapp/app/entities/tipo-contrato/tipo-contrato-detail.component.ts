import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoContrato } from 'app/shared/model/tipo-contrato.model';

@Component({
  selector: 'jhi-tipo-contrato-detail',
  templateUrl: './tipo-contrato-detail.component.html'
})
export class TipoContratoDetailComponent implements OnInit {
  tipoContrato: ITipoContrato | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoContrato }) => (this.tipoContrato = tipoContrato));
  }

  previousState(): void {
    window.history.back();
  }
}
