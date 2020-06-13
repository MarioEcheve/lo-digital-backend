import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUsuarioLibroPerfil } from 'app/shared/model/usuario-libro-perfil.model';

@Component({
  selector: 'jhi-usuario-libro-perfil-detail',
  templateUrl: './usuario-libro-perfil-detail.component.html'
})
export class UsuarioLibroPerfilDetailComponent implements OnInit {
  usuarioLibroPerfil: IUsuarioLibroPerfil | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usuarioLibroPerfil }) => (this.usuarioLibroPerfil = usuarioLibroPerfil));
  }

  previousState(): void {
    window.history.back();
  }
}
