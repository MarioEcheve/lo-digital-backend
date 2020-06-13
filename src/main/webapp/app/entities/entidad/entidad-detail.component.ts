import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEntidad } from 'app/shared/model/entidad.model';

@Component({
  selector: 'jhi-entidad-detail',
  templateUrl: './entidad-detail.component.html'
})
export class EntidadDetailComponent implements OnInit {
  entidad: IEntidad | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entidad }) => (this.entidad = entidad));
  }

  previousState(): void {
    window.history.back();
  }
}
