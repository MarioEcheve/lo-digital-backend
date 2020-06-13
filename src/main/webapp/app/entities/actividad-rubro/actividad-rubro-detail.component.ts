import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActividadRubro } from 'app/shared/model/actividad-rubro.model';

@Component({
  selector: 'jhi-actividad-rubro-detail',
  templateUrl: './actividad-rubro-detail.component.html'
})
export class ActividadRubroDetailComponent implements OnInit {
  actividadRubro: IActividadRubro | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ actividadRubro }) => (this.actividadRubro = actividadRubro));
  }

  previousState(): void {
    window.history.back();
  }
}
