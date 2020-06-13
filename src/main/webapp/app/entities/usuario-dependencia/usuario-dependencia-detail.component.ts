import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUsuarioDependencia } from 'app/shared/model/usuario-dependencia.model';

@Component({
  selector: 'jhi-usuario-dependencia-detail',
  templateUrl: './usuario-dependencia-detail.component.html'
})
export class UsuarioDependenciaDetailComponent implements OnInit {
  usuarioDependencia: IUsuarioDependencia | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usuarioDependencia }) => (this.usuarioDependencia = usuarioDependencia));
  }

  previousState(): void {
    window.history.back();
  }
}
