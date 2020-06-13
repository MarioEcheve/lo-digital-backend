import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPerfilUsuarioDependencia } from 'app/shared/model/perfil-usuario-dependencia.model';

@Component({
  selector: 'jhi-perfil-usuario-dependencia-detail',
  templateUrl: './perfil-usuario-dependencia-detail.component.html'
})
export class PerfilUsuarioDependenciaDetailComponent implements OnInit {
  perfilUsuarioDependencia: IPerfilUsuarioDependencia | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfilUsuarioDependencia }) => (this.perfilUsuarioDependencia = perfilUsuarioDependencia));
  }

  previousState(): void {
    window.history.back();
  }
}
