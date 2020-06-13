import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComuna } from 'app/shared/model/comuna.model';

@Component({
  selector: 'jhi-comuna-detail',
  templateUrl: './comuna-detail.component.html'
})
export class ComunaDetailComponent implements OnInit {
  comuna: IComuna | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comuna }) => (this.comuna = comuna));
  }

  previousState(): void {
    window.history.back();
  }
}
