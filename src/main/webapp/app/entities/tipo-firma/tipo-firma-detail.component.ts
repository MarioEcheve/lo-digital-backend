import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoFirma } from 'app/shared/model/tipo-firma.model';

@Component({
  selector: 'jhi-tipo-firma-detail',
  templateUrl: './tipo-firma-detail.component.html'
})
export class TipoFirmaDetailComponent implements OnInit {
  tipoFirma: ITipoFirma | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoFirma }) => (this.tipoFirma = tipoFirma));
  }

  previousState(): void {
    window.history.back();
  }
}
