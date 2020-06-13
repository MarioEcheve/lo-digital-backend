import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDependencia } from 'app/shared/model/dependencia.model';

@Component({
  selector: 'jhi-dependencia-detail',
  templateUrl: './dependencia-detail.component.html'
})
export class DependenciaDetailComponent implements OnInit {
  dependencia: IDependencia | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dependencia }) => (this.dependencia = dependencia));
  }

  previousState(): void {
    window.history.back();
  }
}
