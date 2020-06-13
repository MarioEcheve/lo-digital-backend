import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUsuarioDependencia, UsuarioDependencia } from 'app/shared/model/usuario-dependencia.model';
import { UsuarioDependenciaService } from './usuario-dependencia.service';
import { IDependencia } from 'app/shared/model/dependencia.model';
import { DependenciaService } from 'app/entities/dependencia/dependencia.service';
import { IUsuario } from 'app/shared/model/usuario.model';
import { UsuarioService } from 'app/entities/usuario/usuario.service';
import { IPerfilUsuarioDependencia } from 'app/shared/model/perfil-usuario-dependencia.model';
import { PerfilUsuarioDependenciaService } from 'app/entities/perfil-usuario-dependencia/perfil-usuario-dependencia.service';

type SelectableEntity = IDependencia | IUsuario | IPerfilUsuarioDependencia;

@Component({
  selector: 'jhi-usuario-dependencia-update',
  templateUrl: './usuario-dependencia-update.component.html'
})
export class UsuarioDependenciaUpdateComponent implements OnInit {
  isSaving = false;
  dependencias: IDependencia[] = [];
  usuarios: IUsuario[] = [];
  perfilusuariodependencias: IPerfilUsuarioDependencia[] = [];

  editForm = this.fb.group({
    id: [],
    fechaCreacion: [],
    fechaModificacion: [],
    estado: [],
    dependencia: [],
    usuario: [],
    perfilUsuarioDependencia: []
  });

  constructor(
    protected usuarioDependenciaService: UsuarioDependenciaService,
    protected dependenciaService: DependenciaService,
    protected usuarioService: UsuarioService,
    protected perfilUsuarioDependenciaService: PerfilUsuarioDependenciaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usuarioDependencia }) => {
      if (!usuarioDependencia.id) {
        const today = moment().startOf('day');
        usuarioDependencia.fechaCreacion = today;
        usuarioDependencia.fechaModificacion = today;
      }

      this.updateForm(usuarioDependencia);

      this.dependenciaService.query().subscribe((res: HttpResponse<IDependencia[]>) => (this.dependencias = res.body || []));

      this.usuarioService.query().subscribe((res: HttpResponse<IUsuario[]>) => (this.usuarios = res.body || []));

      this.perfilUsuarioDependenciaService
        .query()
        .subscribe((res: HttpResponse<IPerfilUsuarioDependencia[]>) => (this.perfilusuariodependencias = res.body || []));
    });
  }

  updateForm(usuarioDependencia: IUsuarioDependencia): void {
    this.editForm.patchValue({
      id: usuarioDependencia.id,
      fechaCreacion: usuarioDependencia.fechaCreacion ? usuarioDependencia.fechaCreacion.format(DATE_TIME_FORMAT) : null,
      fechaModificacion: usuarioDependencia.fechaModificacion ? usuarioDependencia.fechaModificacion.format(DATE_TIME_FORMAT) : null,
      estado: usuarioDependencia.estado,
      dependencia: usuarioDependencia.dependencia,
      usuario: usuarioDependencia.usuario,
      perfilUsuarioDependencia: usuarioDependencia.perfilUsuarioDependencia
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const usuarioDependencia = this.createFromForm();
    if (usuarioDependencia.id !== undefined) {
      this.subscribeToSaveResponse(this.usuarioDependenciaService.update(usuarioDependencia));
    } else {
      this.subscribeToSaveResponse(this.usuarioDependenciaService.create(usuarioDependencia));
    }
  }

  private createFromForm(): IUsuarioDependencia {
    return {
      ...new UsuarioDependencia(),
      id: this.editForm.get(['id'])!.value,
      fechaCreacion: this.editForm.get(['fechaCreacion'])!.value
        ? moment(this.editForm.get(['fechaCreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaModificacion: this.editForm.get(['fechaModificacion'])!.value
        ? moment(this.editForm.get(['fechaModificacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      estado: this.editForm.get(['estado'])!.value,
      dependencia: this.editForm.get(['dependencia'])!.value,
      usuario: this.editForm.get(['usuario'])!.value,
      perfilUsuarioDependencia: this.editForm.get(['perfilUsuarioDependencia'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsuarioDependencia>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
