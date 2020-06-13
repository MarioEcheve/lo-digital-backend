import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModalidad } from 'app/shared/model/modalidad.model';

@Component({
  selector: 'jhi-modalidad-detail',
  templateUrl: './modalidad-detail.component.html'
})
export class ModalidadDetailComponent implements OnInit {
  modalidad: IModalidad | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modalidad }) => (this.modalidad = modalidad));
  }

  previousState(): void {
    window.history.back();
  }
}
