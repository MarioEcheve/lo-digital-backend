import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoEntidad } from 'app/shared/model/tipo-entidad.model';

@Component({
  selector: 'jhi-tipo-entidad-detail',
  templateUrl: './tipo-entidad-detail.component.html'
})
export class TipoEntidadDetailComponent implements OnInit {
  tipoEntidad: ITipoEntidad | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoEntidad }) => (this.tipoEntidad = tipoEntidad));
  }

  previousState(): void {
    window.history.back();
  }
}
