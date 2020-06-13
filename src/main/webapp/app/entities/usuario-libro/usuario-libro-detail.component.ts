import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUsuarioLibro } from 'app/shared/model/usuario-libro.model';

@Component({
  selector: 'jhi-usuario-libro-detail',
  templateUrl: './usuario-libro-detail.component.html'
})
export class UsuarioLibroDetailComponent implements OnInit {
  usuarioLibro: IUsuarioLibro | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usuarioLibro }) => (this.usuarioLibro = usuarioLibro));
  }

  previousState(): void {
    window.history.back();
  }
}
