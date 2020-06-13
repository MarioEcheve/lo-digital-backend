import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILibro } from 'app/shared/model/libro.model';

@Component({
  selector: 'jhi-libro-detail',
  templateUrl: './libro-detail.component.html'
})
export class LibroDetailComponent implements OnInit {
  libro: ILibro | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ libro }) => (this.libro = libro));
  }

  previousState(): void {
    window.history.back();
  }
}
