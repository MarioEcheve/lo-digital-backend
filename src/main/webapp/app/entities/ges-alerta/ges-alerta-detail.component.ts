import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGesAlerta } from 'app/shared/model/ges-alerta.model';

@Component({
  selector: 'jhi-ges-alerta-detail',
  templateUrl: './ges-alerta-detail.component.html'
})
export class GesAlertaDetailComponent implements OnInit {
  gesAlerta: IGesAlerta | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gesAlerta }) => (this.gesAlerta = gesAlerta));
  }

  previousState(): void {
    window.history.back();
  }
}
