import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGesNota } from 'app/shared/model/ges-nota.model';

@Component({
  selector: 'jhi-ges-nota-detail',
  templateUrl: './ges-nota-detail.component.html'
})
export class GesNotaDetailComponent implements OnInit {
  gesNota: IGesNota | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gesNota }) => (this.gesNota = gesNota));
  }

  previousState(): void {
    window.history.back();
  }
}
