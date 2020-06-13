import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUsuario, Usuario } from 'app/shared/model/usuario.model';
import { UsuarioService } from './usuario.service';

@Component({
  selector: 'jhi-usuario-update',
  templateUrl: './usuario-update.component.html'
})
export class UsuarioUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    rut: [null, [Validators.required]],
    nombre: [null, [Validators.required]],
    apellidoPaterno: [null, [Validators.maxLength(100)]],
    apellidoMaterno: [null, [Validators.maxLength(100)]],
    profesionOficio: [null, [Validators.maxLength(80)]],
    emailPrincipal: [null, [Validators.maxLength(50)]],
    emailSecundario: [null, [Validators.maxLength(50)]],
    telefonoPrincipal: [null, [Validators.maxLength(50)]],
    telefonoSecundario: [null, [Validators.maxLength(50)]],
    fechaCreacion: [],
    fechaModificacion: []
  });

  constructor(protected usuarioService: UsuarioService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usuario }) => {
      if (!usuario.id) {
        const today = moment().startOf('day');
        usuario.fechaCreacion = today;
        usuario.fechaModificacion = today;
      }

      this.updateForm(usuario);
    });
  }

  updateForm(usuario: IUsuario): void {
    this.editForm.patchValue({
      id: usuario.id,
      rut: usuario.rut,
      nombre: usuario.nombre,
      apellidoPaterno: usuario.apellidoPaterno,
      apellidoMaterno: usuario.apellidoMaterno,
      profesionOficio: usuario.profesionOficio,
      emailPrincipal: usuario.emailPrincipal,
      emailSecundario: usuario.emailSecundario,
      telefonoPrincipal: usuario.telefonoPrincipal,
      telefonoSecundario: usuario.telefonoSecundario,
      fechaCreacion: usuario.fechaCreacion ? usuario.fechaCreacion.format(DATE_TIME_FORMAT) : null,
      fechaModificacion: usuario.fechaModificacion ? usuario.fechaModificacion.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const usuario = this.createFromForm();
    if (usuario.id !== undefined) {
      this.subscribeToSaveResponse(this.usuarioService.update(usuario));
    } else {
      this.subscribeToSaveResponse(this.usuarioService.create(usuario));
    }
  }

  private createFromForm(): IUsuario {
    return {
      ...new Usuario(),
      id: this.editForm.get(['id'])!.value,
      rut: this.editForm.get(['rut'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      apellidoPaterno: this.editForm.get(['apellidoPaterno'])!.value,
      apellidoMaterno: this.editForm.get(['apellidoMaterno'])!.value,
      profesionOficio: this.editForm.get(['profesionOficio'])!.value,
      emailPrincipal: this.editForm.get(['emailPrincipal'])!.value,
      emailSecundario: this.editForm.get(['emailSecundario'])!.value,
      telefonoPrincipal: this.editForm.get(['telefonoPrincipal'])!.value,
      telefonoSecundario: this.editForm.get(['telefonoSecundario'])!.value,
      fechaCreacion: this.editForm.get(['fechaCreacion'])!.value
        ? moment(this.editForm.get(['fechaCreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaModificacion: this.editForm.get(['fechaModificacion'])!.value
        ? moment(this.editForm.get(['fechaModificacion'])!.value, DATE_TIME_FORMAT)
        : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsuario>>): void {
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
