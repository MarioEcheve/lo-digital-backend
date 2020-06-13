import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoUsuarioDependencia } from 'app/shared/model/tipo-usuario-dependencia.model';

@Component({
  selector: 'jhi-tipo-usuario-dependencia-detail',
  templateUrl: './tipo-usuario-dependencia-detail.component.html'
})
export class TipoUsuarioDependenciaDetailComponent implements OnInit {
  tipoUsuarioDependencia: ITipoUsuarioDependencia | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoUsuarioDependencia }) => (this.tipoUsuarioDependencia = tipoUsuarioDependencia));
  }

  previousState(): void {
    window.history.back();
  }
}
