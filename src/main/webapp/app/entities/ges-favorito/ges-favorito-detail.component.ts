import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGesFavorito } from 'app/shared/model/ges-favorito.model';

@Component({
  selector: 'jhi-ges-favorito-detail',
  templateUrl: './ges-favorito-detail.component.html'
})
export class GesFavoritoDetailComponent implements OnInit {
  gesFavorito: IGesFavorito | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gesFavorito }) => (this.gesFavorito = gesFavorito));
  }

  previousState(): void {
    window.history.back();
  }
}
