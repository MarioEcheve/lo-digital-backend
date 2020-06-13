import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstadoServicio } from 'app/shared/model/estado-servicio.model';

@Component({
  selector: 'jhi-estado-servicio-detail',
  templateUrl: './estado-servicio-detail.component.html'
})
export class EstadoServicioDetailComponent implements OnInit {
  estadoServicio: IEstadoServicio | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoServicio }) => (this.estadoServicio = estadoServicio));
  }

  previousState(): void {
    window.history.back();
  }
}
