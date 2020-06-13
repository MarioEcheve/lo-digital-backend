import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstadoLibro } from 'app/shared/model/estado-libro.model';

@Component({
  selector: 'jhi-estado-libro-detail',
  templateUrl: './estado-libro-detail.component.html'
})
export class EstadoLibroDetailComponent implements OnInit {
  estadoLibro: IEstadoLibro | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoLibro }) => (this.estadoLibro = estadoLibro));
  }

  previousState(): void {
    window.history.back();
  }
}
