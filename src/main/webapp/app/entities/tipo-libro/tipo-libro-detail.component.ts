import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoLibro } from 'app/shared/model/tipo-libro.model';

@Component({
  selector: 'jhi-tipo-libro-detail',
  templateUrl: './tipo-libro-detail.component.html'
})
export class TipoLibroDetailComponent implements OnInit {
  tipoLibro: ITipoLibro | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoLibro }) => (this.tipoLibro = tipoLibro));
  }

  previousState(): void {
    window.history.back();
  }
}
