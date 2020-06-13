import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstadoRespuesta } from 'app/shared/model/estado-respuesta.model';

@Component({
  selector: 'jhi-estado-respuesta-detail',
  templateUrl: './estado-respuesta-detail.component.html'
})
export class EstadoRespuestaDetailComponent implements OnInit {
  estadoRespuesta: IEstadoRespuesta | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoRespuesta }) => (this.estadoRespuesta = estadoRespuesta));
  }

  previousState(): void {
    window.history.back();
  }
}
