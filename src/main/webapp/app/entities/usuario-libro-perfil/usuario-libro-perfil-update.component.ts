import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUsuarioLibroPerfil, UsuarioLibroPerfil } from 'app/shared/model/usuario-libro-perfil.model';
import { UsuarioLibroPerfilService } from './usuario-libro-perfil.service';

@Component({
  selector: 'jhi-usuario-libro-perfil-update',
  templateUrl: './usuario-libro-perfil-update.component.html'
})
export class UsuarioLibroPerfilUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: []
  });

  constructor(
    protected usuarioLibroPerfilService: UsuarioLibroPerfilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usuarioLibroPerfil }) => {
      this.updateForm(usuarioLibroPerfil);
    });
  }

  updateForm(usuarioLibroPerfil: IUsuarioLibroPerfil): void {
    this.editForm.patchValue({
      id: usuarioLibroPerfil.id,
      nombre: usuarioLibroPerfil.nombre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const usuarioLibroPerfil = this.createFromForm();
    if (usuarioLibroPerfil.id !== undefined) {
      this.subscribeToSaveResponse(this.usuarioLibroPerfilService.update(usuarioLibroPerfil));
    } else {
      this.subscribeToSaveResponse(this.usuarioLibroPerfilService.create(usuarioLibroPerfil));
    }
  }

  private createFromForm(): IUsuarioLibroPerfil {
    return {
      ...new UsuarioLibroPerfil(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsuarioLibroPerfil>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
